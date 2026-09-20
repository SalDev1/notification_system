package com.example.notification_delivery_sys.entity;

import com.example.notification_delivery_sys.enums.NotificationCategory;
import com.example.notification_delivery_sys.enums.NotificationChannel;
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
// UniqueConstraints --> Only helps with preventing duplicate database records.
// We Achieve :- A user cannot receive the same notification twice based on unique parameters.
@Table(name = "notification_records",
        uniqueConstraints ={ @UniqueConstraint(columnNames = {"idempotency_key"})})
public class NotificationRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    public String triggerId;

    public String recipient;

    @Enumerated(EnumType.STRING)
    public NotificationChannel channel;

    @Enumerated(EnumType.STRING)
    public NotificationCategory category;

    @Column(name = "idempotency_key", unique = true, nullable = false)
    // triggerId + channel + recipient;
    public String notificationIdempotencyKey;

    public String subject;
    public String message;

    @Enumerated(EnumType.STRING)
    public NotificationStatus status;

    public Date createdAt;
    public Date updatedAt;
    public Number retryCount;
}