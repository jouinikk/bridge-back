package com.example.cars.services;

import com.example.cars.Repositories.PasswordResetTokenRepository;
import com.example.cars.Repositories.UserRepository;
import com.example.cars.entities.PasswordResetToken;
import com.example.cars.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Service
public class PasswordResetService {

    private final PasswordResetTokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private static final int CODE_LENGTH = 6;
    private static final long TOKEN_EXPIRY_MINUTES = 30;

    @Autowired
    public PasswordResetService(PasswordResetTokenRepository tokenRepository,
                                UserRepository userRepository,
                                EmailService emailService,
                                PasswordEncoder passwordEncoder) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void initiatePasswordReset(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        // Generate random 6-digit code
        String resetCode = generateRandomCode();

        // Create or update reset token
        PasswordResetToken token = tokenRepository.findByUser(user)
                .orElse(new PasswordResetToken());

        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        token.setResetCode(resetCode);
        token.setExpiryDate(LocalDateTime.now().plusMinutes(TOKEN_EXPIRY_MINUTES));

        tokenRepository.save(token);

        // Send email with reset code
        sendResetCodeEmail(user, resetCode);
    }

    public boolean verifyResetCode(String email, String code) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        PasswordResetToken token = tokenRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("No reset token found"));

        if (token.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Reset code has expired");
        }

        return token.getResetCode().equals(code);
    }

    @Transactional
    public void resetPassword(String email, String code, String newPassword) {
        if (!verifyResetCode(email, code)) {
            throw new RuntimeException("Invalid reset code");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update password
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        // Delete used token
        tokenRepository.deleteByUser(user);
    }

    private String generateRandomCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }

    private void sendResetCodeEmail(User user, String code) {
        String subject = "Password Reset Code";
        String html = "<h2 style='color:#2E86C1;'>Password Reset Request</h2>" +
                "<p>We received a request to reset your password.</p>" +
                "<p>Your verification code is: <strong>" + code + "</strong></p>" +
                "<p>This code will expire in " + TOKEN_EXPIRY_MINUTES + " minutes.</p>" +
                "<p>If you didn't request this, please ignore this email.</p>" +
                "<hr><small>Envoyé automatiquement depuis le système de gestion</small>";

        emailService.sendHtmlEmail(user.getEmail(), subject, html);
    }
}