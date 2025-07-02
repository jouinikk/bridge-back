package com.example.cars.entities;

import java.util.List;

public class SendNotificationRequest {
    private String title;
    private String message;
    private boolean sendEmail;
    private boolean sendSms;
    private NotificationType type;
    private List<Integer> userIds;

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
    public boolean isSendEmail() {
        return sendEmail;
    }
    public void setSendEmail(boolean sendEmail) {
        this.sendEmail = sendEmail;
    }
    public boolean isSendSms() {
        return sendSms;
    }
    public void setSendSms(boolean sendSms) {
        this.sendSms = sendSms;
    }
    public NotificationType getType() {
        return type;
    }
    public void setType(NotificationType type) {
        this.type = type;
    }
    public List<Integer> getUserIds() {
        return userIds;
    }
}
