package com.example.fcmdemo.controller;

import com.example.fcmdemo.dto.AllDevicesNotificationRequest;
import com.example.fcmdemo.dto.DeviceNotificationRequest;
import com.example.fcmdemo.dto.NotificationSubscriptionRequest;
import com.example.fcmdemo.dto.TopicNotificationRequest;
import com.example.fcmdemo.service.NotificationService;
import com.google.firebase.messaging.FirebaseMessagingException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1/notifications")
@AllArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/send-to-device")
    public ResponseEntity<String> sendNotification(@RequestBody @Valid DeviceNotificationRequest request) {
        try {
            notificationService.sendNotificationToDevice(request);
            return ResponseEntity.ok("Notification sent successfully.");
        } catch (FirebaseMessagingException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send notification.");
        }
    }

    @PostMapping("/send-to-topic")
    public ResponseEntity<String> sendNotificationToTopic(@RequestBody @Valid TopicNotificationRequest request) {
        try {
            notificationService.sendPushNotificationToTopic(request);
            return ResponseEntity.ok("Notification sent successfully.");
        } catch (FirebaseMessagingException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send notification.");
        }
    }

    @PostMapping("/send-to-all")
    public ResponseEntity<String> sendNotificationToAll(@RequestBody @Valid AllDevicesNotificationRequest request) {
        try {
            notificationService.sendMulticastNotification(request);
            return ResponseEntity.ok("Multicast notification sent successfully.");
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send multicast notification.");
        }
    }

    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribeToTopic(@RequestBody @Valid NotificationSubscriptionRequest request) {
        try {
            notificationService.subscribeDeviceToTopic(request);
            return ResponseEntity.ok("Device subscribed to the topic successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to subscribe device to the topic.");
        }
    }

    @PostMapping("/unsubscribe")
    public ResponseEntity<String> unsubscribeFromTopic(@RequestBody @Valid NotificationSubscriptionRequest request) {
        try {
            notificationService.unsubscribeDeviceFromTopic(request);
            return ResponseEntity.ok("Device unsubscribed from the topic successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to unsubscribe device from the topic.");
        }
    }
}