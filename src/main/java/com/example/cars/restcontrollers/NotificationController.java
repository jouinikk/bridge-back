package com.example.cars.restcontrollers;

import com.example.cars.Repositories.NotificationRepository;
import com.example.cars.Repositories.UserRepository;
import com.example.cars.entities.Notification;
import com.example.cars.entities.NotificationStatus;
import com.example.cars.entities.SendNotificationRequest;
import com.example.cars.entities.User;
import com.example.cars.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationService service;
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationController(NotificationService service, NotificationRepository notificationRepository, UserRepository userRepository) {
        this.service = service;
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/user/{userId}")
    public List<Notification> getUserNotifications(@PathVariable Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return notificationRepository.findByUserOrderByCreatedAtDesc(user);
    }

//    @PostMapping("/{id}/read")
//    public void markAsRead(@PathVariable Long id) {
//        service.markAsRead(id);
//    }
    @PostMapping("/{id}/read")
    public ResponseEntity<?> markAsRead(@PathVariable Long id) {
        service.markAsRead(id); // mark as read logic
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{userId}/unread-count")
    public Long getUnreadCount(@PathVariable Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return service.countUnread(user);
    }

    @PostMapping
    public List<Notification> sendNotification(@RequestBody SendNotificationRequest request) {
        List<User> users;

        if (request.getUserIds() == null || request.getUserIds().isEmpty()) {
            // Send to all users
            users = userRepository.findAll();
        } else {
            // Send to selected users
            users = request.getUserIds().stream()
                    .map(id -> userRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("User not found with ID: " + id)))
                    .collect(Collectors.toList());
        }

        return service.sendNotificationToUsers(
                request.getTitle(),
                request.getMessage(),
                request.getType(),
                request.isSendEmail(),
                request.isSendSms(),
                users
        );
    }
    @PostMapping("/mark-all-as-read")
    public void markAllAsRead(@RequestBody Map<String, Integer> body) {
        Integer userId = body.get("userId");
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        service.markAllAsRead(user);
    }
}

