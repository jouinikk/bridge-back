package com.example.cars.python;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DailyScheduler {
    // Run CompetitionScriptRunner daily at 3 AM
    @Scheduled(cron = "0 0 3 * * ?",zone = "Africa/Tunis")  // Second, Minute, Hour, Day, Month, Day of Week
    public void runCompetitionScript() {
        System.out.println("Executing CompetitionScriptRunner at: " + java.time.LocalDateTime.now());
        try{CompetitionScriptRunner.main(new String[]{});
    } catch (Exception e) {
        e.printStackTrace();
    }
    }

    // Run ResultScriptRunner daily at 3 AM
    @Scheduled(cron = "0 0 3 * * ?",zone = "Africa/Tunis")
    public void runResultScript() {
        System.out.println("Executing ResultScriptRunner at: " + java.time.LocalDateTime.now());
        try{ResultScriptRunner.main(new String[]{});
    } catch (Exception e) {
        e.printStackTrace();
    }
    }
}