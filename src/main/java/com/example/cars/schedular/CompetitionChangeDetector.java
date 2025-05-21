package com.example.cars.schedular;

import com.example.cars.Repositories.CompetitionRepository;
import com.example.cars.entities.Competition;
import com.example.cars.entities.NotificationType;
import com.example.cars.entities.UserInfo;
import com.example.cars.services.NotificationService;
import com.example.cars.services.UserInfoService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CompetitionChangeDetector {
    private final CompetitionRepository competitionRepository;
    private final NotificationService notificationService;
    private final UserInfoService userInfoService;

    // Tracks the last seen competition modification timestamps
    private final Map<Long, LocalDateTime> lastSeenCompetitions = new ConcurrentHashMap<>();

    public CompetitionChangeDetector(CompetitionRepository competitionRepository,
                                     NotificationService notificationService,
                                     UserInfoService userInfoService) {
        this.competitionRepository = competitionRepository;
        this.notificationService = notificationService;
        this.userInfoService = userInfoService;
    }

    @Scheduled(fixedRate = 60000)
    public void detectChangesInCompetitions() {
        List<Competition> competitions = competitionRepository.findAll();
        List<UserInfo> users = userInfoService.getAllUserInfos();

        for (Competition comp : competitions) {
            boolean notify = false;
            String title = "";
            String msg = formatCompetition(comp);

            if (!comp.isCreatedNotificationSent()) {
                title = "Nouvelle compétition ajoutée";
                notificationService.sendNotificationToUsers(title, msg, NotificationType.COMPETITION, true, false, users);
                comp.setCreatedNotificationSent(true);
                notify = true;
            } else if (wasUpdated(comp)) {
                title = "Mise à jour de la compétition";
                notificationService.sendNotificationToUsers(title, msg, NotificationType.COMPETITION, true, false, users);
                comp.setUpdatedNotificationSent(true);
                notify = true;
                System.out.println("Update detected for comp " + comp.getId()
                        + ": lastModified = " + comp.getLastModified()
                        + ", dateImportation = " + comp.getDateImportation());
            }

            if (notify) {
                competitionRepository.save(comp);
                System.out.println("Saved comp with ID: " + comp.getId() + ", createdNotificationSent: " + comp.isCreatedNotificationSent());
            }
        }
    }
    private boolean wasUpdated(Competition comp) {
        return !comp.isUpdatedNotificationSent()
                && comp.getLastModified() != null
                && comp.getDateImportation() != null
                && comp.getLastModified().isAfter(comp.getDateImportation());
    }

    private String formatCompetition(Competition c) {
        return "🏊 Compétition: " + c.getNom() + "\n" +
                "📅 Du " + c.getDateDebut() + " au " + c.getDateFin() + "\n" +
                "📍 Lieu: " + c.getLieu() + "\n" +
                (c.getDescription() != null ? "📝 " + c.getDescription() + "\n" : "") +
                (c.getUrlSource() != null ? "🔗 Source: " + c.getUrlSource() : "");
    }
}
