package com.example.notification_delivery_sys.service.kafka.channel_consumers;

import com.example.notification_delivery_sys.event.KafkaNotificationEvent;
import com.example.notification_delivery_sys.metrics.NotificationMetrics;
import com.example.notification_delivery_sys.utils.JsonUtils;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class SMSConsumer {

    @Autowired
    public JsonUtils jsonUtils;

    @Autowired
    public NotificationMetrics notificationMetrics;

    @KafkaListener(topics = "notification.channel.sms", groupId = "notifications-test-group")
    @Retry(name = "smsChannel")
    @Bulkhead(name="smsChannel", type = Bulkhead.Type.SEMAPHORE)
    public void consumeMessageThroughSMSChannel(String message) {
        KafkaNotificationEvent req = jsonUtils.deserialize(message, KafkaNotificationEvent.class);
        notificationMetrics.notificationSmsChannelRequests();
        System.out.println("Notification sent through SMS");
    }
}
