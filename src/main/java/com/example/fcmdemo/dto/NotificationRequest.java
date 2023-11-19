package com.example.fcmdemo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class NotificationRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String body;
    private String imageUrl;
    private Map<String, String> data = new HashMap<>();
}
