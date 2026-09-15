package com.example.notification_delivery_sys.dto.notification;

import com.example.notification_delivery_sys.entity.NotificationTemplate;
import com.example.notification_delivery_sys.enums.NotificationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRes {
    public UUID notificationId;
    public NotificationStatus status;
//    public Map<String, NotificationTemplate> template;
    public NotificationTemplate template;
}
