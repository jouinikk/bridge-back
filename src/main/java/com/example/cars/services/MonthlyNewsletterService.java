package com.example.cars.services;

import com.example.cars.Repositories.CompetitionRepository;
import com.example.cars.Repositories.MonthlyNewsletterRepository;
import com.example.cars.Repositories.UserRepository;
import com.example.cars.entities.Competition;
import com.example.cars.entities.MonthlyNewsletter;
import com.example.cars.entities.Resultat;
import com.example.cars.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MonthlyNewsletterService {

    private final CompetitionRepository competitionRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final MonthlyNewsletterRepository newsletterRepository;

    private final String[] tips = new String[]{
            "Drink plenty of water before training.",
            "Warm up properly to avoid injury.",
            "Improve your dolphin kick technique.",
            "Consistency beats intensity in swimming.",
            "Track your times to monitor progress.",
            "Use proper breathing rhythm.",
            "Eat well the night before a competition.",
            "Work on your flip turns.",
            "Rest is as important as training.",
            "Never skip leg day in swimming.",
            "Balance strength with flexibility.",
            "Always end with a cool-down swim."
    };

    @Scheduled(cron = "0 0 8 1 * ?") // Every 1st of the month at 08:00
    public void sendMonthlyNewsletter() {
        YearMonth now = YearMonth.now();

        if (newsletterRepository.findByMonth(now).isPresent()) return; // Already sent

        LocalDateTime start = now.atDay(1).atStartOfDay();
        LocalDateTime end = now.plusMonths(1).atDay(1).atStartOfDay().minusSeconds(1);

        List<Competition> competitions = competitionRepository.findByDateImportationBetween(start, end);
        List<Resultat> results = competitions.stream()
                .flatMap(c -> c.getResultats().stream())
                .collect(Collectors.toList());

        String tip = tips[now.getMonthValue() - 1];
        String htmlContent = generateHtml(now, competitions, results, tip);

        List<User> users = userRepository.findAll();
        for (User user : users) {
            emailService.sendHtmlEmail(user.getEmail(), "Newsletter - " + now.getMonth(), htmlContent);
        }

        MonthlyNewsletter newsletter = new MonthlyNewsletter();
        newsletter.setMonth(now);
        newsletter.setSentAt(LocalDateTime.now());
        newsletter.setContentHtml(htmlContent);
        newsletter.setTipOfTheMonth(tip);
        newsletter.setCompetitionsCount(competitions.size());
        newsletter.setResultsCount(results.size());

        newsletterRepository.save(newsletter);
    }

    private String generateHtml(YearMonth month, List<Competition> competitions, List<Resultat> results, String tip) {
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html><html><head><style>")
                .append("body{font-family:Arial,sans-serif;background:#f0f8ff;padding:20px;}")
                .append(".header{background:#0077b6;color:white;padding:20px;border-radius:10px 10px 0 0;}")
                .append(".section{margin:20px 0;}")
                .append("h2{color:#023e8a;border-bottom:2px solid #caf0f8;}")
                .append("ul{padding-left:20px;}")
                .append(".footer{text-align:center;color:#777;font-size:0.8em;margin-top:30px;}")
                .append("</style></head><body>")
                .append("<div class='header'><h1>🏊‍♂️ Newsletter - ")
                .append(month.getMonth().getDisplayName(TextStyle.FULL, Locale.FRENCH)).append(" ")
                .append(month.getYear()).append("</h1><p>La natation en Tunisie, centralisée pour vous.</p></div>");

        sb.append("<div class='section'><h2>📅 Compétitions</h2><ul>");
        for (Competition c : competitions) {
            sb.append("<li><strong>").append(c.getNom()).append("</strong> – ")
                    .append(c.getDateDebut()).append(" – ").append(c.getLieu()).append("</li>");
        }
        sb.append("</ul></div>");

        sb.append("<div class='section'><h2>🏆 Résultats</h2><ul>");
        results.stream().limit(10).forEach(r -> {
            sb.append("<li>").append(r.getNomNageur()).append(" – ")
                    .append(r.getTemps()).append(" – ")
                    .append(r.getClub()).append("</li>");
        });
        sb.append("</ul></div>");

        sb.append("<div class='section'><h2>💡 Astuce du Mois</h2><p><em>")
                .append(tip).append("</em></p></div>");

        sb.append("<div class='footer'>Ce mail vous a été envoyé par la plateforme natation.tn</div></body></html>");

        return sb.toString();
    }
    public void sendTestNewsletter() {
        YearMonth now = YearMonth.now();

        LocalDateTime start = now.atDay(1).atStartOfDay();
        LocalDateTime end = now.plusMonths(1).atDay(1).atStartOfDay().minusSeconds(1);

        List<Competition> competitions = competitionRepository.findByDateImportationBetween(start, end);
        List<Resultat> results = competitions.stream()
                .flatMap(c -> c.getResultats().stream())
                .collect(Collectors.toList());

        String tip = tips[now.getMonthValue() - 1];
        String htmlContent = generateHtml(now, competitions, results, tip);

        List<User> users = userRepository.findAll();
        for (User user : users) {
            emailService.sendHtmlEmail(user.getEmail(), "TEST Newsletter - " + now.getMonth(), htmlContent);
        }

        System.out.println("✅ Test newsletter sent to all users.");
        MonthlyNewsletter testNewsletter = MonthlyNewsletter.builder()
                .month(now)
                .sentAt(LocalDateTime.now())
                .contentHtml(htmlContent)
                .tipOfTheMonth(tip)
                .competitionsCount(competitions.size())
                .resultsCount(results.size())
                .build();

        newsletterRepository.save(testNewsletter);
    }
    public List<MonthlyNewsletter> getAllNewsletters() {
        return newsletterRepository.findAll(Sort.by(Sort.Direction.DESC, "month"));
    }

    public Optional<MonthlyNewsletter> getNewsletterById(Long id) {
        return newsletterRepository.findById(id);
    }

}

