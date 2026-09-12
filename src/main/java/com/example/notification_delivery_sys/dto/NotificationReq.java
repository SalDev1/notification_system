package com.example.notification_delivery_sys.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationReq {

    @NotBlank(message = "Please enter valid recipient")
    public String recipient;

    @NotBlank(message = "Please enter valid channel name")
    public String channel;

    @NotBlank(message = "Please enter valid subject")
    public String subject;

    @NotBlank(message = "Please enter valid message")
    public String message;
}
