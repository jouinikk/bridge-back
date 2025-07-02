package com.example.cars.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender sender) {
        this.mailSender = sender;
    }

    public void sendHtmlEmail(String to, String subject, String htmlContent) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(
                    message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name()
            );
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true); // true = HTML

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace(); // Or use a logger
        }
    }
    public void sendHtmlEmailBatch(List<String> recipients, String subject, String htmlContent) {
        for (String to : recipients) {
            try {
                sendHtmlEmail(to, subject, htmlContent);
                System.out.println("✅ Email sent to: " + to);
            } catch (Exception e) {
                System.err.println("❌ Failed to send email to: " + to + " - " + e.getMessage());
            }
        }
    }
}