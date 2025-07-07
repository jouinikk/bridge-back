package com.example.cars.config;

import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import java.util.TimeZone;

@Configuration
public class TimezoneConfig {
    
    @PostConstruct
    public void init() {
        // Set the default timezone for the entire application
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Paris"));
        System.out.println("Application timezone set to: " + TimeZone.getDefault().getID());
    }
}
