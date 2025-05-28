package com.example.cars.dto;

import com.example.cars.entities.NotificationType;

public class WebSocketNotification {
    private String title;
    private String message;
    private NotificationType type;

    public WebSocketNotification(String title, String message, NotificationType type) {
        this.title = title;
        this.message = message;
        this.type = type;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public NotificationType getType() {
        return type;
    }
    public void setType(NotificationType type) {
        this.type = type;
    }
    // Getters and setters
}

