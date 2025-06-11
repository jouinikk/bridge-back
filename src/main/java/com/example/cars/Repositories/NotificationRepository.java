package com.example.cars.Repositories;

import com.example.cars.entities.Notification;
import com.example.cars.entities.NotificationStatus;
import com.example.cars.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserIdOrderByCreatedAtDesc(User user);
    Long countByUserIdAndStatus(User user, NotificationStatus status);
    List<Notification> findByUserIdAndStatus(User user, NotificationStatus status);
    List<Notification> findByUserOrderByCreatedAtDesc(User user);
}
