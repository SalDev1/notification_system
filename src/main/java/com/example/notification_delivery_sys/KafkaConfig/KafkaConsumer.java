package com.example.notification_delivery_sys.KafkaConfig;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    @KafkaListener(topics = "notification.created", groupId = "your-consumer-group-id")
    public void consumeMessage(String message) {
        System.out.println("Received Consumer message : " + message);
    }
}
