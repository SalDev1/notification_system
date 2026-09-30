package com.example.notification_delivery_sys.service.kafka.channel_consumers;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class SMSConsumer {

    @KafkaListener(topics = "notification.channel.sms", groupId = "notifications-test-group")
    public void consumeMessageThroughSMSChannel(String message) {
        System.out.println("Notification sent through SMS");
    }
}
