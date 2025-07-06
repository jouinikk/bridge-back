package com.example.cars.schedular;

import com.example.cars.Repositories.SeanceBienEtreRepository;
import com.example.cars.entities.SeanceBienEtre;
import com.example.cars.services.EmailService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class SeanceReminderScheduler {

    private final SeanceBienEtreRepository seanceRepo;
    private final EmailService emailService;

    @Scheduled(fixedRate = 60000) // every hour
    public void sendSeanceReminders() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime in48h = now.plusHours(48);

        Date startDate = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(in48h.atZone(ZoneId.systemDefault()).toInstant());

        System.out.println("🔍 Checking for séances between " + now + " and " + in48h);

        List<SeanceBienEtre> seances = seanceRepo.findSeancesForReminder(startDate, endDate);

        for (SeanceBienEtre seance : seances) {
            System.out.println("➡️ Found séance: " + seance.getId() + " at " + seance.getDate());
            System.out.println("   ➤ reminderSent: " + seance.isReminderSent());
            System.out.println("   ➤ instructeur: " + seance.getInstructeur());
            System.out.println("   ➤ email: " + seance.getEmailInstructeur());

            if (!seance.isReminderSent() && seance.getEmailInstructeur() != null && !seance.getEmailInstructeur().isBlank()) {
                String subject = "Rappel : Séance dans 48h";
                String html = "<h2>Bonjour " + seance.getInstructeur() + ",</h2>" +
                        "<p>Un rappel que vous avez une séance de <strong>" + seance.getType().name().toLowerCase().replace("_", " ") + "</strong> prévue dans 48h.</p>" +
                        "<ul>" +
                        "<li><strong>Date :</strong> " + seance.getDate() + "</li>" +
                        "<li><strong>Durée :</strong> " + seance.getDuree() + " minutes</li>" +
                        "<li><strong>Capacité :</strong> " + seance.getCapacite() + "</li>" +
                        "<li><strong>Niveau :</strong> " + seance.getNiveau() + "</li>" +
                        "</ul>" +
                        "<p>Merci de nous prévenir au moins 24h avant en cas d’indisponibilité.</p>" +
                        "<hr><small>Message automatique du système.</small>";

                System.out.println("📧 Sending email to " + seance.getEmailInstructeur());
                emailService.sendHtmlEmail(seance.getEmailInstructeur(), subject, html);

                seance.setReminderSent(true);
                seanceRepo.save(seance);

                System.out.println("✅ Email sent and reminderSent updated for séance ID " + seance.getId());
            }
        }
    }

    // Run the method manually at app startup to test immediately
    @PostConstruct
    public void testNow() {
        System.out.println("⚡ Forcing initial reminder check at startup");
        sendSeanceReminders();
    }
}
