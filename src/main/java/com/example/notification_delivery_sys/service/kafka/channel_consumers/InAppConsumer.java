package com.example.notification_delivery_sys.service.kafka.channel_consumers;

import com.example.notification_delivery_sys.dto.notification.NotificationReq;
import com.example.notification_delivery_sys.event.KafkaNotificationEvent;
import com.example.notification_delivery_sys.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class InAppConsumer {

    @Autowired
    public JsonUtils jsonUtils;

    @KafkaListener(topics = "notification.channel.inapp", groupId = "notifications-test-group")
    public void consumeMessageThroughInAppChannel(String message) {
        KafkaNotificationEvent req = jsonUtils.deserialize(message, KafkaNotificationEvent.class);
        System.out.println("deseralize record ===" + req);
//        The below code helps with pushing the record with dlt topic and invoking retry mechanism by intentionally calling an exception.
//        if(req.getRecipient().equals("salman123@example.com")) {
//            throw new RuntimeException("Invalid Reception");
//        }
        System.out.println("Notification sent through InApp");
    }
}
