package com.example.cars.restcontrollers;

import com.example.cars.services.MonthlyNewsletterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/newsletter")
public class NewsletterController {
    private final MonthlyNewsletterService monthlyNewsletterService;

    @PostMapping("/test")
    public ResponseEntity<String> sendTestNewsletter() {
        monthlyNewsletterService.sendTestNewsletter();
        return ResponseEntity.ok("Test newsletter sent.");
    }
}

