package com.example.notification_delivery_sys.entity;

import com.example.notification_delivery_sys.enums.NotificationCategory;
import com.example.notification_delivery_sys.enums.NotificationChannel;
import com.example.notification_delivery_sys.enums.NotificationOutboxStatus;
import com.example.notification_delivery_sys.enums.NotificationPriority;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "outbox_events")
public class NotificationOutboxEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID eventId;

    public String notificationEventId;

    public String payload;

    @Enumerated(EnumType.STRING)
    public NotificationOutboxStatus status;

    public Instant createdAt;
    public Instant publishedAt;
}
