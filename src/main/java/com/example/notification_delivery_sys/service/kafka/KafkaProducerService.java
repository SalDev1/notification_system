package com.example.notification_delivery_sys.service.kafka;

import com.example.notification_delivery_sys.dto.notification.NotificationReq;
import com.example.notification_delivery_sys.enums.NotificationPriority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class KafkaProducerService {
    // KafkaTemplate :- It's a spring kafka producer helps to publish records to Kafka platform.
    private static final Logger log = LoggerFactory.getLogger(KafkaProducerService.class);
    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(NotificationReq notificationRequest) {
        if(notificationRequest.getPriority() == NotificationPriority.HIGH) {
              kafkaTemplate.send("notification.priority.high", "High");
          } else if(notificationRequest.getPriority() == NotificationPriority.MEDIUM) {
              kafkaTemplate.send("notification.priority.medium", "Medium");
          } else if(notificationRequest.getPriority() == NotificationPriority.LOW) {
              kafkaTemplate.send("notification.priority.low", "Low");
          }
    }
}
