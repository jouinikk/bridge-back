package com.example.cars.python;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ResultScriptRunner {
    /**
     * Executes a Python script in the system terminal/command prompt
     * @param scriptPath Path to the Python script
     * @param waitForCompletion If true, waits for script to complete before returning
     * @return The script output if waitForCompletion is true, otherwise null
     */
    public static String runPythonScript(String scriptPath, boolean waitForCompletion) {
        try {
            // Determine the operating system
            String os = System.getProperty("os.name").toLowerCase();

            // Build the command to run Python script
            String command = "python " + scriptPath;

            // Prepare the process builder based on OS
            ProcessBuilder processBuilder;
            if (os.contains("win")) {
                // For Windows
                processBuilder = new ProcessBuilder("cmd.exe", "/c", command);
            } else {
                // For Linux/Mac
                processBuilder = new ProcessBuilder("/bin/bash", "-c", command);
            }

            // Redirect error stream to output stream
            processBuilder.redirectErrorStream(true);

            // Start the process
            Process process = processBuilder.start();

            if (waitForCompletion) {
                // Read the output
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(process.getInputStream()));

                StringBuilder output = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }

                // Wait for the process to complete
                int exitCode = process.waitFor();
                output.append("\nProcess exited with code: ").append(exitCode);

                return output.toString();
            } else {
                // Don't wait, just return null
                return null;
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Error executing Python script: " + e.getMessage();
        }
    }

    public static void main(String[] args) {
        // Example usage - running tes.py
        String scriptPath = "C:\\Users\\LENOVO\\Desktop\\importrequests1.py";
        System.out.println("Executing Python script: " + scriptPath);
        String output = runPythonScript(scriptPath, true);

        System.out.println("Script output:\n" + output);
    }
}
