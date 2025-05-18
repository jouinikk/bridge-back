package com.example.cars.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId; // ynjm ykoun Coach or Swimmer

    private String title;
    private String message;

    @Enumerated(EnumType.STRING)
    private NotificationType type; // SESSION, COMPETITION, OTHER

    @Enumerated(EnumType.STRING)
    private NotificationStatus status; // UNREAD, READ

    private LocalDateTime createdAt;

    private boolean sentByEmail;
    private boolean sentBySms;

    public Notification() {
        this.createdAt = LocalDateTime.now();
        this.status = NotificationStatus.UNREAD;
        this.sentByEmail = false;
        this.sentBySms = false;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public NotificationStatus getStatus() {
        return status;
    }

    public void setStatus(NotificationStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isSentByEmail() {
        return sentByEmail;
    }

    public void setSentByEmail(boolean sentByEmail) {
        this.sentByEmail = sentByEmail;
    }
    public boolean isSentBySms() {
        return sentBySms;
    }
    public void setSentBySms(boolean sentBySms) {
        this.sentBySms = sentBySms;
    }
}
