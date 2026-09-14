package com.example.notification_delivery_sys.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationTemplate {
    String templateId;
    String channel;
    String subjectTemplate;
    String bodyTemplate;
    String version;
}
