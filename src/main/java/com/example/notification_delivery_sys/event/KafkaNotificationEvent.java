package com.example.notification_delivery_sys.event;

import com.example.notification_delivery_sys.enums.NotificationCategory;
import com.example.notification_delivery_sys.enums.NotificationChannel;
import com.example.notification_delivery_sys.enums.NotificationPriority;
import com.example.notification_delivery_sys.enums.NotificationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KafkaNotificationEvent {
    @Id
    public String notificationEventId;

    public String recipient;

    @Enumerated(EnumType.STRING)
    public NotificationChannel channel;

    @Enumerated(EnumType.STRING)
    public NotificationCategory category;

    @Enumerated(EnumType.STRING)
    public NotificationPriority priority;

    @Enumerated(EnumType.STRING)
    public NotificationStatus status;

    public Date createdAt;
    public Date completedAt;
}
