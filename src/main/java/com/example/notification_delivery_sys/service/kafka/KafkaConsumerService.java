package com.example.notification_delivery_sys.service.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerService.class);

    @KafkaListener(topics = "notification.priority.high", groupId = "notifications-test-group")
    public void consumeHighPriorityMessage(String message) {
        System.out.println("Received High Priority Message : " + message);
    }
    @KafkaListener(topics = "notification.priority.medium", groupId = "notifications-test-group")
    public void consumeMediumPriorityMessage(String message) {
        System.out.println("Received Medium Priority Message " + message);
    }
    @KafkaListener(topics = "notification.priority.low", groupId = "notifications-test-group")
    public void consumeLowPriorityMessage(String message) {
        System.out.println("Received Low Priority Message " + message);
    }
}
