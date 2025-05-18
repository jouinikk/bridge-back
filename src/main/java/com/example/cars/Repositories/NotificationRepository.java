package com.example.cars.Repositories;

import com.example.cars.entities.Notification;
import com.example.cars.entities.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserIdOrderByCreatedAtDesc(Long userId);
    Long countByUserIdAndStatus(Long userId, NotificationStatus status);
}
