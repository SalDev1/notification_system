package com.example.notification_delivery_sys.service;

import com.example.notification_delivery_sys.dto.notification.NotificationReq;
import com.example.notification_delivery_sys.dto.notification.NotificationRes;
import com.example.notification_delivery_sys.entity.NotificationRecord;
import com.example.notification_delivery_sys.entity.NotificationTemplate;
import com.example.notification_delivery_sys.entity.UserPreferenceRecord;
import com.example.notification_delivery_sys.enums.NotificationStatus;
import com.example.notification_delivery_sys.repository.NotificationRepository;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
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

    @Autowired
    public NotificationRepository notificationRepository;

    @Autowired
    public UserPreferenceService userPreferenceService;

    @Override
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

            String newTriggerId = UUID.randomUUID().toString();

            String uniqueIdempotentRawKey = newTriggerId + "|"
                    + notificationRequest.getRecipient() + "|"
                    + notificationRequest.getChannel();

            String uniqueIdempotentHashKey = Hashing
                    .sha256()
                    .hashString(uniqueIdempotentRawKey, StandardCharsets.UTF_8)
                    .toString();

            NotificationRecord newNotificationRecord = new NotificationRecord()
                    .builder()
                    .status(NotificationStatus.PROCESSING)
                    .message(notificationRequest.getMessage())
                    .triggerId(newTriggerId)
                    .notificationIdempotencyKey(uniqueIdempotentHashKey)
                    .category(notificationRequest.getCategory())
                    .channel(notificationRequest.getChannel())
                    .recipient(notificationRequest.getRecipient())
                    .subject(notificationRequest.getSubject())
                    .createdAt(new Date())
                    .updatedAt(new Date())
                    .retryCount(0)
                    .build();

            notificationRepository.save(newNotificationRecord);

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
        } catch (Data)
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
