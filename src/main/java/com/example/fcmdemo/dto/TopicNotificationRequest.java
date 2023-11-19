package com.example.fcmdemo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TopicNotificationRequest extends NotificationRequest {
    @NotBlank
    private String topicName;
}
