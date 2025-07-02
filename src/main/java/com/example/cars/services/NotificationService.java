package com.example.cars.services;

import com.example.cars.Repositories.NotificationRepository;
import com.example.cars.entities.*;
import jakarta.transaction.Transactional;
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
    public List<Notification> getNotificationsForUser(User user) {
        return repo.findByUserIdOrderByCreatedAtDesc(user);
    }
    @Transactional
    public List<Notification> sendNotificationToUsers(String title, String message, NotificationType type, boolean sendEmail, boolean sendSms, List<User> users) {
        List<Notification> notifications = new ArrayList<>();

        for (User user : users) {
            Notification notif = new Notification();
            notif.setUser(user);
            notif.setTitle(title);
            notif.setType(type);
            notif.setMessage(message);
            notif.setSentByEmail(sendEmail);
            notif.setSentBySms(sendSms);
            notif.setStatus(NotificationStatus.UNREAD);
            notif.setCreatedAt(LocalDateTime.now());
            System.out.println("Saving notification for user " + user.getId() + ", title=" + title);
            Notification saved = repo.save(notif);
            System.out.println("Notification saved with ID = " + saved.getId());
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
        Notification notif = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notif.setStatus(NotificationStatus.READ);
        repo.save(notif);
    }
    public Long countUnread(User user) {
        return repo.countByUserIdAndStatus(user, NotificationStatus.UNREAD);
    }
    @Transactional
    public void markAllAsRead(User user) {
        List<Notification> unreadNotifs = repo.findByUserIdAndStatus(user, NotificationStatus.UNREAD);
        for (Notification notif : unreadNotifs) {
            notif.setStatus(NotificationStatus.READ);
        }
        repo.saveAll(unreadNotifs);
    }
}
