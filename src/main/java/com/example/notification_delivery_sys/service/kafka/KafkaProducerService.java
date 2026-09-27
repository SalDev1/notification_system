package com.example.notification_delivery_sys.service.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaProducerService {
    // KafkaTemplate :- It's a spring kafka producer helps to publish records to Kafka platform.
    private static final Logger log = LoggerFactory.getLogger(KafkaProducerService.class);
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String topic_name = "notification.created";

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message) {
        CompletableFuture<SendResult<String,String>> futureResult = kafkaTemplate.send(topic_name, message);
        futureResult.whenComplete((result, ex) -> {
            if(ex == null) {
                log.info("Sent key = [{}] value=[{}] to Partition=[{}] with Offset=[{}]",
                        topic_name,
                        message,
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset()
                );
            } else {
                log.error("Unable to send message due to : {}" ,ex.getMessage());
            }
        });
    }
}
