package com.example.cars.restcontrollers;

import com.example.cars.entities.Notification;
import com.example.cars.entities.SendNotificationRequest;
import com.example.cars.services.NotificationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin("*")
public class NotificationController {
    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @GetMapping("/user/{userId}")
    public List<Notification> getUserNotifications(@PathVariable Long userId) {
        return service.getNotificationsForUser(userId);
    }

    @PostMapping("/{id}/read")
    public void markAsRead(@PathVariable Long id) {
        service.markAsRead(id);
    }

    @GetMapping("/user/{userId}/unread-count")
    public Long getUnreadCount(@PathVariable Long userId) {
        return service.countUnread(userId);
    }
    @PostMapping
    public List<Notification> sendNotification(@RequestBody SendNotificationRequest request) {
        return service.sendNotificationToUsers(
                request.getTitle(),
                request.getMessage(),
                request.isSendEmail(),
                request.isSendSms(),
                request.getUsers()
        );
    }
}
