package com.example.cars.restcontrollers;

import com.example.cars.Repositories.NotificationRepository;
import com.example.cars.entities.Notification;
import com.example.cars.entities.NotificationStatus;
import com.example.cars.entities.SendNotificationRequest;
import com.example.cars.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationService service;
    private final NotificationRepository notificationRepository;

    public NotificationController(NotificationService service, NotificationRepository notificationRepository) {
        this.service = service;
        this.notificationRepository = notificationRepository;
    }

    @GetMapping("/user/{userId}")
    public List<Notification> getUserNotifications(@PathVariable Long userId) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
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
                request.getType(),
                request.isSendEmail(),
                request.isSendSms(),
                request.getUsers()
        );
    }
    @PostMapping("/mark-all-as-read")
    public void markAllAsRead(@RequestBody Map<String, Long> body) {
        Long userId = body.get("userId");
        service.markAllAsRead(userId);
    }

}

