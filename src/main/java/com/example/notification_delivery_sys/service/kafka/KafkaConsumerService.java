package com.example.notification_delivery_sys.service.kafka;

import com.example.notification_delivery_sys.dto.notification.NotificationReq;
import com.example.notification_delivery_sys.entity.NotificationRecord;
import com.example.notification_delivery_sys.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class KafkaConsumerService {
    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerService.class);
    private final KafkaTemplate<String, String> kafkaTemplate;
    public JsonUtils jsonUtils;

    public KafkaConsumerService(KafkaTemplate<String, String> kafkaTemplate, JsonUtils jsonUtils) {
        this.kafkaTemplate = kafkaTemplate;
        this.jsonUtils = jsonUtils;
    }

    Map<String, String> channel_topics = Map.of(
            "IN_APP", "notification.channel.inapp",
            "EMAIL", "notification.channel.email",
            "SMS", "notification.channel.sms"
    );

    public NotificationReq fetchDeserializedNotificationReq(String message) {
        NotificationReq notif_record = jsonUtils.deserialize(message, NotificationReq.class);
        return notif_record;
    }

    @KafkaListener(topics = "notification.priority.high", groupId = "notifications-test-group")
    public void consumeHighPriorityMessage(String message) {
        System.out.println("Received High Priority Message : " + message);
        NotificationReq fetchNotifRequest = fetchDeserializedNotificationReq(message);
        kafkaTemplate.send(channel_topics.get(fetchNotifRequest.getChannel().toString()), message);
    }
    @KafkaListener(topics = "notification.priority.medium", groupId = "notifications-test-group")
    public void consumeMediumPriorityMessage(String message) {
        System.out.println("Received Medium Priority Message " + message);
        NotificationReq fetchNotifRequest = fetchDeserializedNotificationReq(message);
        kafkaTemplate.send(channel_topics.get(fetchNotifRequest.getChannel().toString()), message);
    }
    @KafkaListener(topics = "notification.priority.low", groupId = "notifications-test-group")
    public void consumeLowPriorityMessage(String message) {
        System.out.println("Received Low Priority Message " + message);
        NotificationReq fetchNotifRequest = fetchDeserializedNotificationReq(message);
        kafkaTemplate.send(channel_topics.get(fetchNotifRequest.getChannel().toString()), message);
    }
}
