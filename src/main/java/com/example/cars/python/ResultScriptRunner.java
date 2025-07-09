package com.example.cars.python;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ResultScriptRunner {
    public static void main(String[] args) {
        try {
            // Load the resource as stream
            InputStream scriptStream = CompetitionScriptRunner.class.getResourceAsStream("/importrequests1.py");

            if (scriptStream == null) {
                System.err.println("Script not found in resources!");
                return;
            }

            // Copy to a temp file
            File tempScript = File.createTempFile("importrequests1", ".py");
            Files.copy(scriptStream, tempScript.toPath(), StandardCopyOption.REPLACE_EXISTING);
            tempScript.setExecutable(true);

            // Execute the script
            ProcessBuilder pb = new ProcessBuilder("python", tempScript.getAbsolutePath());
            pb.redirectErrorStream(true);
            Process process = pb.start();

            // Read output
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }

            int exitCode = process.waitFor();
            System.out.println("Script exited with code: " + exitCode);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
