package com.example.notification_delivery_sys.service.kafka.channel_consumers;


import com.example.notification_delivery_sys.event.KafkaNotificationEvent;
import com.example.notification_delivery_sys.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EmailConsumer {

    @Autowired
    public JsonUtils jsonUtils;

    @KafkaListener(topics = "notification.channel.email", groupId = "notifications-test-group")
    public void consumeMessageThroughEmailChannel(String message) {
        KafkaNotificationEvent req = jsonUtils.deserialize(message, KafkaNotificationEvent.class);

        System.out.println("Notification sent through Email");
    }
}
