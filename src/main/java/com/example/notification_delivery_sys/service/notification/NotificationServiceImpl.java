package com.example.notification_delivery_sys.service.notification;

import com.example.notification_delivery_sys.dto.notification.NotificationReq;
import com.example.notification_delivery_sys.dto.notification.NotificationRes;
import com.example.notification_delivery_sys.entity.NotificationOutboxEvent;
import com.example.notification_delivery_sys.entity.NotificationRecord;
import com.example.notification_delivery_sys.entity.NotificationTemplate;
import com.example.notification_delivery_sys.entity.UserPreferenceRecord;
import com.example.notification_delivery_sys.enums.NotificationOutboxStatus;
import com.example.notification_delivery_sys.enums.NotificationStatus;
import com.example.notification_delivery_sys.event.KafkaNotificationEvent;
import com.example.notification_delivery_sys.repository.notification.NotificationRepository;
import com.example.notification_delivery_sys.repository.notification.OutboxNotificationRepository;
import com.example.notification_delivery_sys.service.kafka.KafkaProducerService;
import com.example.notification_delivery_sys.service.user_preference.UserPreferenceService;
import com.example.notification_delivery_sys.utils.JsonUtils;
import com.google.common.hash.Hashing;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.*;

@Service
public class NotificationServiceImpl implements NotificationService {

    Map<String, NotificationTemplate> notificationTemplates = Map.of(
            "IN_APP_PROMOTIONAL", new NotificationTemplate().builder().templateId("IN_APP_PROMOTIONAL").build(),
            "EMAIL_PROMOTIONAL", new NotificationTemplate().builder().templateId("EMAIL_PROMOTIONAL").build(),
            "SMS_PROMOTIONAL", new NotificationTemplate().builder().templateId("SMS_PROMOTIONAL").build(),
            "SMS_TRANSACTIONAL", new NotificationTemplate().builder().templateId("SMS_TRANSACTIONAL").build(),
            "IN_APP_TRANSACTIONAL", new NotificationTemplate().builder().templateId("IN_APP_TRANSACTIONAL").build(),
            "EMAIL_TRANSACTIONAL", new NotificationTemplate().builder().templateId("EMAIL_TRANSACTIONAL").build()
    );

    public ArrayList<String> createUniqueIdempotentKey(NotificationReq notificationRequest) {
        String newTriggerId = UUID.randomUUID().toString();

        String uniqueIdempotentRawKey = newTriggerId + "|"
                + notificationRequest.getRecipient() + "|"
                + notificationRequest.getChannel();

        String uniqueIdempotentHashKey = Hashing
                .sha256()
                .hashString(uniqueIdempotentRawKey, StandardCharsets.UTF_8)
                .toString();

        ArrayList<String> result = new ArrayList<String>();
        result.add(newTriggerId);
        result.add(uniqueIdempotentHashKey);

        return result;
    }

    @Autowired
    public NotificationRepository notificationRepository;

    @Autowired
    public OutboxNotificationRepository outboxNotificationRepository;

    @Autowired
    public UserPreferenceService userPreferenceService;

//    @Autowired
//    private KafkaProducerService kafkaProducerService;

    @Autowired
    private JsonUtils jsonUtils;

    @Override
    @Transactional // Ensures atomicity by ensuring everything works well or rollback everything if something goes wrong.
    public NotificationRes saveNotificationResponse(NotificationReq notificationRequest) {
        try {
            Optional<UserPreferenceRecord> userPreference = userPreferenceService
                    .findByUserIdByChannelByCategory(
                            notificationRequest.getUserId(),
                            notificationRequest.getChannel() ,
                            notificationRequest.getCategory());

            System.out.println("user preference console.log" + userPreference);

            if(userPreference.isPresent() && !userPreference.get().isEnabled()) {
                System.out.println("User not allowed to receive notification");
                return null;
            }

            ArrayList<String> uniqueIdempotentKeyWithHash = createUniqueIdempotentKey(notificationRequest);

            NotificationRecord newNotificationRecord = new NotificationRecord()
                    .builder()
                    .status(NotificationStatus.PROCESSING)
                    .message(notificationRequest.getMessage())
                    .triggerId(uniqueIdempotentKeyWithHash.get(0))
                    .notificationIdempotencyKey(uniqueIdempotentKeyWithHash.get(1))
                    .category(notificationRequest.getCategory())
                    .channel(notificationRequest.getChannel())
                    .recipient(notificationRequest.getRecipient())
                    .subject(notificationRequest.getSubject())
                    .createdAt(new Date())
                    .updatedAt(new Date())
                    .retryCount(0)
                    .build();

            notificationRepository.save(newNotificationRecord);

            KafkaNotificationEvent event = new KafkaNotificationEvent()
                    .builder()
                    .notificationEventId(newNotificationRecord.getNotificationIdempotencyKey())
                    .status(NotificationStatus.PROCESSING)
                    .category(notificationRequest.getCategory())
                    .channel(notificationRequest.getChannel())
                    .priority(notificationRequest.getPriority())
                    .createdAt(new Date())
                    .build();

            NotificationOutboxEvent outboxEvent = new NotificationOutboxEvent()
                    .builder()
                    .notificationEventId(newNotificationRecord.getNotificationIdempotencyKey())
                    .status(NotificationOutboxStatus.PENDING)
                    .payload(jsonUtils.serialize(event))
                    .createdAt(Instant.now())
                    .build();

            outboxNotificationRepository.save(outboxEvent);
//            kafkaProducerService.sendMessage(event);  // With OutBox Event, We are not directly pushing API event request to Kafka broker.

            System.out.println("notif request" + notificationRequest);

            NotificationTemplate finalResponseTemplate = notificationTemplates
                    .get(notificationRequest.getChannel() + "_" + notificationRequest.getCategory());

            finalResponseTemplate.setSubject(notificationRequest.getSubject());
            finalResponseTemplate.setBody(notificationRequest.getMessage());

            NotificationRes finalHttpResponse = new NotificationRes()
                    .builder()
                    .notificationId(newNotificationRecord.getId())
                    .status(NotificationStatus.ACCEPTED)
//                .template(Map.of("template", finalResponseTemplate))
                    .template(finalResponseTemplate)
                    .build();
            return finalHttpResponse;

        } catch (DataIntegrityViolationException ex) {
            return null;
        }
    }

    @Override
    public Optional<NotificationRecord> findNotificationDetailsById (UUID notificationId) {
        return notificationRepository.findById(notificationId);
    }

    @Override
    public Optional<NotificationRecord> fetchLatestStatusByNotificationId(UUID notificationId) {
        return notificationRepository.findById(notificationId);
    }
}
