package com.example.notification_delivery_sys.service.kafka.channel_consumers;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class InAppConsumer {

    @KafkaListener(topics = "notification.channel.inapp", groupId = "notifications-test-group")
    public void consumeMessageThroughInAppChannel(String message) {
        System.out.println("Notification sent through InApp");
    }
}
