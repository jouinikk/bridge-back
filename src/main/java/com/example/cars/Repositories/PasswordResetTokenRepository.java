package com.example.cars.Repositories;

import com.example.cars.entities.PasswordResetToken;
import com.example.cars.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByUser(User user);
    void deleteByUser(User user);
}