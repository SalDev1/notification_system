package com.example.notification_delivery_sys.service.kafka;

import com.example.notification_delivery_sys.entity.NotificationOutboxEvent;
import com.example.notification_delivery_sys.enums.NotificationOutboxStatus;
import com.example.notification_delivery_sys.enums.NotificationPriority;
import com.example.notification_delivery_sys.enums.NotificationStatus;
import com.example.notification_delivery_sys.event.KafkaNotificationEvent;
import com.example.notification_delivery_sys.metrics.NotificationMetrics;
import com.example.notification_delivery_sys.repository.notification.OutboxNotificationRepository;
import com.example.notification_delivery_sys.utils.JsonUtils;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KafkaOutboxProducerService {

    //  Core Idea :- Reads out all the committed Outbox records in the DB and pushes them into Kafka
    // KafkaTemplate :- It's a spring kafka producer helps to publish records to Kafka platform.
    private static final Logger log = LoggerFactory.getLogger(KafkaOutboxProducerService.class);
    private final KafkaTemplate<String, String> kafkaTemplate;
    private JsonUtils jsonUtils;
    public OutboxNotificationRepository outboxNotificationRepository;
    public NotificationMetrics notificationMetrics;

    public KafkaOutboxProducerService(KafkaTemplate<String, String> kafkaTemplate, JsonUtils jsonUtils, OutboxNotificationRepository outboxNotificationRepository, NotificationMetrics notificationMetrics) {
        this.kafkaTemplate = kafkaTemplate;
        this.jsonUtils = jsonUtils;
        this.outboxNotificationRepository = outboxNotificationRepository;
        this.notificationMetrics = notificationMetrics;
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

    public void notificationMetricCallback(KafkaNotificationEvent event) {
        NotificationPriority priority = event.getPriority();
        switch (priority) {
            case HIGH -> notificationMetrics.notificationHighPriorityRequests();
            case MEDIUM -> notificationMetrics.notificationMediumPriorityRequests();
            case LOW -> notificationMetrics.notificationLowPriorityRequests();
        }
    }

    //    Polls after every 2 seconds.
    @Scheduled(fixedRate = 2000)
    @Transactional
    public void publishKafkaOutBoxEvent() {
        List<NotificationOutboxEvent> outBoxEvents = outboxNotificationRepository.findAllByStatus(NotificationOutboxStatus.PENDING);
        System.out.println("Notif Outbox events " + outBoxEvents.size());

        for(NotificationOutboxEvent event : outBoxEvents) {
            KafkaNotificationEvent notifEvent = jsonUtils.deserialize(event.getPayload(), KafkaNotificationEvent.class);
            try {
                kafkaTemplate.send(resolveKafkaPriorityTopic(notifEvent), event.getPayload());
                event.setStatus(NotificationOutboxStatus.PUBLISHED);
                notificationMetricCallback(notifEvent);
                outboxNotificationRepository.save(event);
            } catch(Exception e) {
                System.out.println("Failed to publish outbox event " + event);
            }
        }
    }
}
