package com.example.notification_delivery_sys.service.kafka;

import com.example.notification_delivery_sys.dto.notification.NotificationReq;
import com.example.notification_delivery_sys.entity.NotificationRecord;
import com.example.notification_delivery_sys.entity.ProcessedNotificationRecord;
import com.example.notification_delivery_sys.enums.NotificationStatus;
import com.example.notification_delivery_sys.event.KafkaNotificationEvent;
import com.example.notification_delivery_sys.repository.notification.ProcessedNotificationRepository;
import com.example.notification_delivery_sys.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class KafkaConsumerService {
    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerService.class);
    private final KafkaTemplate<String, String> kafkaTemplate;
    public JsonUtils jsonUtils;

    @Autowired
    private ProcessedNotificationRepository processedNotificationRepository;

    public KafkaConsumerService(KafkaTemplate<String, String> kafkaTemplate, JsonUtils jsonUtils) {
        this.kafkaTemplate = kafkaTemplate;
        this.jsonUtils = jsonUtils;
    }

    Map<String, String> channel_topics = Map.of(
            "IN_APP", "notification.channel.inapp",
            "EMAIL", "notification.channel.email",
            "SMS", "notification.channel.sms"
    );

    public KafkaNotificationEvent fetchDeserializedEvent(String message) {
        KafkaNotificationEvent notif_record = jsonUtils.deserialize(message, KafkaNotificationEvent.class);
        return notif_record;
    }

    public void createNotificationProcessedRecord (KafkaNotificationEvent notifEvent) {
        ProcessedNotificationRecord newRecord = new ProcessedNotificationRecord().builder()
                .notificationId(notifEvent.getNotificationEventId())
                .status(NotificationStatus.ACCEPTED)
                .processedAt(new Date())
                .build();
        processedNotificationRepository.save(newRecord);
    }

    @KafkaListener(topics = "notification.priority.high", groupId = "notifications-test-group")
    public void consumeHighPriorityMessage(String message) {
        System.out.println("Received High Priority Message : " + message);
        KafkaNotificationEvent notifEvent = fetchDeserializedEvent(message);
        if(processedNotificationRepository.existsById(notifEvent.getNotificationEventId())) {
            throw new RuntimeException("Notification already been processed");
        } else {
            createNotificationProcessedRecord(notifEvent);
            kafkaTemplate.send(channel_topics.get(notifEvent.getChannel().toString()), message);
        }
    }
    @KafkaListener(topics = "notification.priority.medium", groupId = "notifications-test-group")
    public void consumeMediumPriorityMessage(String message) {
        System.out.println("Received Medium Priority Message " + message);
        KafkaNotificationEvent notifEvent = fetchDeserializedEvent(message);
        if(processedNotificationRepository.existsById(notifEvent.getNotificationEventId())) {
            throw new RuntimeException("Notification already been processed");
        } else {
            createNotificationProcessedRecord(notifEvent);
            kafkaTemplate.send(channel_topics.get(notifEvent.getChannel().toString()), message);
        }
    }
    @KafkaListener(topics = "notification.priority.low", groupId = "notifications-test-group")
    public void consumeLowPriorityMessage(String message) {
        System.out.println("Received Low Priority Message " + message);
        KafkaNotificationEvent notifEvent = fetchDeserializedEvent(message);
        if(processedNotificationRepository.existsById(notifEvent.getNotificationEventId())) {
            throw new RuntimeException("Notification already been processed");
        } else {
            createNotificationProcessedRecord(notifEvent);
            kafkaTemplate.send(channel_topics.get(notifEvent.getChannel().toString()), message);
        }
    }
}
