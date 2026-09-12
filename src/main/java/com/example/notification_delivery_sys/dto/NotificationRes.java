package com.example.notification_delivery_sys.dto;

import com.example.notification_delivery_sys.entity.NotificationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRes {
    public UUID notificationId;
    public NotificationStatus status;
}
