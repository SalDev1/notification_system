package com.example.notification_delivery_sys.entity;

import com.example.notification_delivery_sys.enums.NotificationCategory;
import com.example.notification_delivery_sys.enums.NotificationChannel;
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
@Table(name = "user_preferences")
public class UserPreferenceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID userId;

    @Enumerated(EnumType.STRING)
    public NotificationChannel channel;

    @Enumerated(EnumType.STRING)
    public NotificationCategory category;

    public boolean enabled;

    public Date quiteHoursStart;
    public Date quiteHoursEnd;
    public String timezone;
}
