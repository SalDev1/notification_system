package com.example.notification_delivery_sys.service.kafka;

import com.example.notification_delivery_sys.dto.notification.NotificationReq;
import com.example.notification_delivery_sys.enums.NotificationPriority;
import com.example.notification_delivery_sys.event.KafkaNotificationEvent;
import com.example.notification_delivery_sys.repository.notification.ProcessedNotificationRepository;
import com.example.notification_delivery_sys.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    public String resolveKafkaPriorityTopic(KafkaNotificationEvent event) {
        NotificationPriority priority = event.getPriority();
        return switch(priority.toString()){
            case "HIGH" -> "notification.priority.high";
            case "MEDIUM" -> "notification.priority.medium";
            case "LOW" -> "notification.priority.low";
            default -> "";
        };
    }

    public void sendMessage(KafkaNotificationEvent event) {
        String kafkaSerializedMessage = jsonUtils.serialize(event);
        kafkaTemplate.send(resolveKafkaPriorityTopic(event), kafkaSerializedMessage);
    }
}
