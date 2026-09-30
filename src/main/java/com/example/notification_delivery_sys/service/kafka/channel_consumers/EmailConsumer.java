package com.example.notification_delivery_sys.service.kafka.channel_consumers;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EmailConsumer {
    @KafkaListener(topics = "notification.channel.email", groupId = "notifications-test-group")
    public void consumeMessageThroughEmailChannel(String message) {
        System.out.println("Notification sent through Email");
    }
}
