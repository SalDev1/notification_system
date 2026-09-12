package com.example.notification_delivery_sys.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "notification_records")
public class NotificationRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;
    public String recipient;
    public String channel;
    public String subject;
    public String message;
    public NotificationStatus status;
    public Date createdAt;
    public Date updatedAt;
    public Number retryCount;
}