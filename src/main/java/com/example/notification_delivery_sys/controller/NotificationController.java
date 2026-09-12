package com.example.notification_delivery_sys.controller;

import com.example.notification_delivery_sys.dto.NotificationReq;
import com.example.notification_delivery_sys.dto.NotificationRes;
import com.example.notification_delivery_sys.entity.NotificationRecord;
import com.example.notification_delivery_sys.entity.NotificationStatus;
import com.example.notification_delivery_sys.exception.NotificationNotFoundException;
import com.example.notification_delivery_sys.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/")
public class NotificationController {

    @Autowired
    public NotificationService notificationService;

    @PostMapping("/v1/notification")
    public ResponseEntity<NotificationRes> createNotification(@Valid @RequestBody NotificationReq request) {
        NotificationRes notificationResponse = notificationService.saveNotificationResponse(request);
        return ResponseEntity.ok(notificationResponse);
    }

    @GetMapping("/v1/notification/getDetails")
    public ResponseEntity<NotificationRecord> getNotificationDetailsById(@RequestParam String id) {
            UUID convertStringIntoUUID = UUID.fromString(id);
            NotificationRecord successRecordResponse = notificationService
                    .findNotificationDetailsById(convertStringIntoUUID)
                    .orElseThrow(() -> new NotificationNotFoundException("Notification Not Found with id" + id));
            return ResponseEntity.ok(successRecordResponse);
    }

    @GetMapping("/v1/notification/status")
    public ResponseEntity<NotificationStatus> getNotificationStatusById(@RequestParam String id) {
        UUID convertStringIntoUUID = UUID.fromString(id);
        NotificationRecord statusResponse = notificationService
                .fetchLatestStatusByNotificationId(convertStringIntoUUID)
                .orElseThrow(() -> new NotificationNotFoundException("Notification Not Found with id" + id));

        return ResponseEntity.ok(statusResponse.getStatus());
    }
}
