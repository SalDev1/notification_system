package com.example.notification_delivery_sys.dto.notification;

import com.example.notification_delivery_sys.enums.NotificationCategory;
import com.example.notification_delivery_sys.enums.NotificationChannel;
import com.example.notification_delivery_sys.enums.NotificationPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationReq {

    @NotNull(message = "Please enter valid user Id")
    public UUID userId;

    @NotBlank(message = "Please enter valid recipient")
    public String recipient;

    public NotificationPriority priority;

    @NotNull(message = "Please enter valid channel name")
    public NotificationChannel channel;

    @NotNull(message = "Please enter valid category")
    public NotificationCategory category;

    @NotBlank(message = "Please enter valid subject")
    public String subject;

    @NotBlank(message = "Please enter valid message")
    public String message;
}
