package com.example.fcmdemo.service;

import com.example.fcmdemo.dto.AllDevicesNotificationRequest;
import com.example.fcmdemo.dto.DeviceNotificationRequest;
import com.example.fcmdemo.dto.NotificationSubscriptionRequest;
import com.example.fcmdemo.dto.TopicNotificationRequest;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@AllArgsConstructor
@Slf4j
public class NotificationService {

    private final FirebaseApp firebaseApp;

    public void sendNotificationToDevice(DeviceNotificationRequest request) throws FirebaseMessagingException, ExecutionException, InterruptedException {
        Message fcmMessage = Message.builder()
                .setToken(request.getDeviceToken())
                .setNotification(
                        Notification.builder()
                                .setTitle(request.getTitle())
                                .setBody(request.getBody())
                                .setImage(request.getImageUrl())
                                .build()
                )
                .putAllData(request.getData())
                .build();

        String response = FirebaseMessaging.getInstance(firebaseApp).sendAsync(fcmMessage).get();
        log.info("sendNotificationToDevice response: {}", response);
    }

    public void sendPushNotificationToTopic(TopicNotificationRequest request) throws FirebaseMessagingException, ExecutionException, InterruptedException {
        Message fcmMessage = Message.builder()
                .setTopic(request.getTopicName())
                .setNotification(
                        Notification.builder()
                                .setTitle(request.getTitle())
                                .setBody(request.getBody())
                                .setImage(request.getImageUrl())
                                .build()
                )
                .setAndroidConfig(getAndroidConfig(request.getTopicName()))
                .setApnsConfig(getApnsConfig(request.getTopicName()))
                .putAllData(request.getData())
                .build();

        String response = FirebaseMessaging.getInstance(firebaseApp).sendAsync(fcmMessage).get();
        log.info("sendNotificationToDevice response: {}", response);
    }


    public void sendMulticastNotification(AllDevicesNotificationRequest request) throws FirebaseMessagingException {
        MulticastMessage multicastMessage = MulticastMessage.builder()
                .addAllTokens(request.getDeviceTokenList().isEmpty() ? getAllDeviceTokens() : request.getDeviceTokenList())
                .setNotification(
                        Notification.builder()
                                .setTitle(request.getTitle())
                                .setBody(request.getBody())
                                .setImage(request.getImageUrl())
                                .build()
                )
                .putAllData(request.getData())
                .build();

        BatchResponse response = FirebaseMessaging.getInstance(firebaseApp).sendEachForMulticast(multicastMessage);
        // Process the response
        for (SendResponse sendResponse : response.getResponses()) {
            if (sendResponse.isSuccessful()) {
                log.info("Message sent successfully to: {}", sendResponse.getMessageId());
            } else {
                log.info("Failed to send message to: {}", sendResponse.getMessageId());
                log.error("Error details: {}", sendResponse.getException().getMessage());
            }
        }
    }

    public void subscribeDeviceToTopic(NotificationSubscriptionRequest request) throws FirebaseMessagingException {
        FirebaseMessaging.getInstance().subscribeToTopic(
                Collections.singletonList(request.getDeviceToken()),
                request.getTopicName()
        );
    }

    public void unsubscribeDeviceFromTopic(NotificationSubscriptionRequest request) throws FirebaseMessagingException {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(
                Collections.singletonList(request.getDeviceToken()),
                request.getTopicName()
        );
    }

    private List<String> getAllDeviceTokens() {
        // Implement logic to retrieve all device tokens from your database or storage
        // Return a list of device tokens
        return new ArrayList<>();
    }

    private AndroidConfig getAndroidConfig(String topic) {
        return AndroidConfig.builder()
                .setTtl(Duration.ofMinutes(2).toMillis()).setCollapseKey(topic)
                .setPriority(AndroidConfig.Priority.HIGH)
                .setNotification(AndroidNotification.builder().setSound("default")
                        .setColor("#FFFF00").setTag(topic).build()).build();
    }

    private ApnsConfig getApnsConfig(String topic) {
        return ApnsConfig.builder()
                .setAps(Aps.builder().setCategory(topic).setThreadId(topic).build()).build();
    }
}
