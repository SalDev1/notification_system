package com.example.notification_delivery_sys.dto.user_preference;

import com.example.notification_delivery_sys.enums.NotificationCategory;
import com.example.notification_delivery_sys.enums.NotificationChannel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdatePreferenceReq {
    public UUID userId;
    public NotificationChannel channel;
    public NotificationCategory category;
    public boolean enabled;
}
