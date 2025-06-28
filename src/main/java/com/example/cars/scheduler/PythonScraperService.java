package com.example.cars.scheduler;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@ConfigurationProperties(prefix = "python")
public class PythonScraperService {

    @Getter @Setter
    private String path;

    @Getter @Setter
    private Scripts scripts;

    @Getter @Setter
    public static class Scripts {
        private String competitions;
        private String results;
    }

    public void runCompetitionScraper() {
        runPythonScript(scripts.getCompetitions());
    }

    public void runResultsScraper() {
        runPythonScript(scripts.getResults());
    }

    private void runPythonScript(String scriptName) {
        Process process = null;
        try {
            ClassPathResource resource = new ClassPathResource(scriptName);
            String scriptPath = resource.getFile().getAbsolutePath();

            ProcessBuilder processBuilder = new ProcessBuilder(
                    path,
                    scriptPath
            );
            processBuilder.redirectErrorStream(true);
            process = processBuilder.start();

            // Read output stream
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    log.info("[PYTHON] {}", line);
                }
            }

            boolean exited = process.waitFor(5, TimeUnit.MINUTES);
            if (!exited) {
                log.error("Python script {} timed out", scriptName);
                process.destroyForcibly();
            } else if (process.exitValue() != 0) {
                log.error("Python script {} failed with exit code {}", scriptName, process.exitValue());
            }

        } catch (IOException e) {
            log.error("IO Error executing Python script {}", scriptName, e);
        } catch (InterruptedException e) {
            log.error("Python script {} execution interrupted", scriptName, e);
            Thread.currentThread().interrupt();
            if (process != null) {
                process.destroyForcibly();
            }
        } catch (Exception e) {
            log.error("Unexpected error running Python script {}", scriptName, e);
        }
    }
}