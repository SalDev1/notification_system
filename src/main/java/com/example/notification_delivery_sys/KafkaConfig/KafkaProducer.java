package com.example.notification_delivery_sys.KafkaConfig;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {
    // KafkaTemplate :- It's a spring kafka producer helps to publish records to Kafka platform.
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String topic_name = "notification.created";

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    public void sendMessage(String message) {
        kafkaTemplate.send(topic_name, message);
        System.out.println();
    }
}
