package com.example.notification_delivery_sys.entity;

import com.example.notification_delivery_sys.enums.NotificationCategory;
import com.example.notification_delivery_sys.enums.NotificationChannel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Array;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationTemplate {
    String templateId;
    String subject;
    String body;
    String version;
    Date createdAt;
    Date updatedAt;
}
