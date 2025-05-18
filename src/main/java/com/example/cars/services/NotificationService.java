package com.example.cars.services;

import com.example.cars.Repositories.NotificationRepository;
import com.example.cars.entities.Notification;
import com.example.cars.entities.NotificationStatus;
import com.example.cars.entities.UserInfo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    private final NotificationRepository repo;
    private final EmailService emailService;
    private final SmsService smsService;

    public NotificationService(NotificationRepository repo, EmailService emailService, SmsService smsService) {
        this.repo = repo;
        this.emailService = emailService;
        this.smsService = smsService;
    }
    public Notification createNotification(Notification notif) {
        notif.setStatus(NotificationStatus.UNREAD);
        notif.setCreatedAt(LocalDateTime.now());
        return repo.save(notif);
    }
    public List<Notification> getNotificationsForUser(Long userId) {
        return repo.findByUserIdOrderByCreatedAtDesc(userId);
    }
    public List<Notification> sendNotificationToUsers(String title, String message, boolean sendEmail, boolean sendSms, List<UserInfo> users) {
        List<Notification> notifications = new ArrayList<>();

        for (UserInfo user : users) {
            Notification notif = new Notification();
            notif.setUserId(user.getUserId());
            notif.setTitle(title);
            notif.setMessage(message);
            notif.setSentByEmail(sendEmail);
            notif.setSentBySms(sendSms);
            notif.setStatus(NotificationStatus.UNREAD);
            notif.setCreatedAt(LocalDateTime.now());

            Notification saved = repo.save(notif);
            notifications.add(saved);

            if (sendEmail && user.getEmail() != null) {
                String subject = "Nouvelle Notification : " + title;
                String html = "<h2 style='color:#2E86C1;'>Nouvelle Notification</h2>" +
                        "<p><strong>" + title + "</strong></p>" +
                        "<p>" + message + "</p>" +
                        "<hr><small>Envoyé automatiquement depuis le système de gestion</small>";
                emailService.sendHtmlEmail(user.getEmail(), subject, html);
            }

            if (sendSms && user.getPhone() != null) {
                String smsMsg = "📢 " + title + ": " + message;
                smsService.sendSms(user.getPhone(), smsMsg);
            }
        }

        return notifications;
    }


    public void markAsRead(Long id) {
        Notification notif = repo.findById(id).orElseThrow();
        notif.setStatus(NotificationStatus.READ);
        repo.save(notif);
    }

    public Long countUnread(Long userId) {
        return repo.countByUserIdAndStatus(userId, NotificationStatus.UNREAD);
    }
}
