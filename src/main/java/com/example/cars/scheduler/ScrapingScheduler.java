package com.example.cars.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnProperty(name = "scheduling.competitions.enabled", havingValue = "true")
public class ScrapingScheduler {

    private final PythonScraperService pythonScraperService;

    public ScrapingScheduler(PythonScraperService pythonScraperService) {
        this.pythonScraperService = pythonScraperService;
    }

    @Scheduled(cron = "${scheduling.competitions.cron}")
    public void dailyScrapingJob() {
        log.info("Starting scheduled scraping job...");
        try {
            pythonScraperService.runCompetitionScraper();
            pythonScraperService.runResultsScraper();
            log.info("Scheduled scraping job completed successfully");
        } catch (Exception e) {
            log.error("Scheduled scraping job failed", e);
        }
    }
}