package com.example.notification_delivery_sys.service.kafka;

import com.example.notification_delivery_sys.dto.notification.NotificationReq;
import com.example.notification_delivery_sys.enums.NotificationPriority;
import com.example.notification_delivery_sys.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class KafkaProducerService {
    // KafkaTemplate :- It's a spring kafka producer helps to publish records to Kafka platform.
    private static final Logger log = LoggerFactory.getLogger(KafkaProducerService.class);
    private final KafkaTemplate<String, String> kafkaTemplate;
    private JsonUtils jsonUtils;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate, JsonUtils jsonUtils) {
        this.kafkaTemplate = kafkaTemplate;
        this.jsonUtils = jsonUtils;
    }

    public void sendMessage(NotificationReq notificationRequest) {
        String kafkaSerializedMessage = jsonUtils.serialize(notificationRequest);

        if(notificationRequest.getPriority() == NotificationPriority.HIGH) {
              kafkaTemplate.send("notification.priority.high", kafkaSerializedMessage);
          } else if(notificationRequest.getPriority() == NotificationPriority.MEDIUM) {
              kafkaTemplate.send("notification.priority.medium", kafkaSerializedMessage);
          } else if(notificationRequest.getPriority() == NotificationPriority.LOW) {
              kafkaTemplate.send("notification.priority.low", kafkaSerializedMessage);
          }
    }
}
