package com.example.cars.entities;

import java.util.List;

public class SendNotificationRequest {
    private String title;
    private String message;
    private boolean sendEmail;
    private boolean sendSms;
    private List<UserInfo> users;
    private NotificationType type;

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
    public List<UserInfo> getUsers() {
        return users;
    }
    public void setUsers(List<UserInfo> users) {
        this.users = users;
    }
    public NotificationType getType() {
        return type;
    }
    public void setType(NotificationType type) {
        this.type = type;
    }
}
