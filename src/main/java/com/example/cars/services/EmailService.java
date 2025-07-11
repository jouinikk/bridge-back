package com.example.cars.services;

import com.example.cars.entities.SeanceBienEtre;
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
    public void sendInstructorSeanceEmail(SeanceBienEtre seance) {
        if (seance.getEmailInstructeur() == null || seance.getEmailInstructeur().isBlank()) return;

        String subject = "Nouvelle séance prévue : " + seance.getType().name();

        String content = """
    <div style="font-family: Arial, sans-serif; padding: 20px; background-color: #f9f9f9;">
        <div style="max-width: 600px; margin: auto; background-color: #ffffff; padding: 20px; border-radius: 8px; border: 1px solid #ddd;">
            <h2 style="color: #2E86C1;">Bonjour  """ + seance.getInstructeur() + """
            ,</h2>
            <p style="font-size: 16px;">Vous avez été assigné(e) à une nouvelle séance de 
                <strong style="color: #1A5276;">""" + seance.getType().name().toLowerCase().replace("_", " ") + """
            </strong>.
            </p>
            <table style="width: 100%; font-size: 15px; border-collapse: collapse; margin-top: 15px;">
                <tr>
                    <td style="padding: 8px 0;"><strong>Date :</strong></td>
                    <td>""" + seance.getDate().toString() + """
                </td>
                </tr>
                <tr>
                    <td style="padding: 8px 0;"><strong>Durée :</strong></td>
                    <td>""" + seance.getDuree() + """ 
                minutes</td>
                </tr>
                <tr>
                    <td style="padding: 8px 0;"><strong>Capacité :</strong></td>
                    <td>""" + seance.getCapacite() + """
                </td>
                </tr>
                <tr>
                    <td style="padding: 8px 0;"><strong>Niveau :</strong></td>
                    <td>""" + seance.getNiveau() + """
                </td>
                </tr>
            </table>
            <p style="margin-top: 20px; font-size: 15px;">Merci et à bientôt !</p>
            <hr style="margin-top: 30px;">
            <p style="font-size: 12px; color: #888;">Message automatique du système. Veuillez ne pas répondre à cet email.</p>
        </div>
    </div>
    """;

        sendHtmlEmail(seance.getEmailInstructeur(), subject, content);
    }

}