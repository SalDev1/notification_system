package com.example.notification_delivery_sys.entity;

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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "processed_notification_records", uniqueConstraints ={ @UniqueConstraint(columnNames = {"notificationId"})})
public class ProcessedNotificationRecord {

    @Id
    @Column(nullable = false, updatable = false)
    String notificationId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    NotificationStatus status;

    Date processedAt;
}
