package com.example.cars.restcontrollers;

import com.example.cars.entities.MonthlyNewsletter;
import com.example.cars.services.MonthlyNewsletterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping
    public ResponseEntity<List<MonthlyNewsletter>> getAllNewsletters() {
        return ResponseEntity.ok(monthlyNewsletterService.getAllNewsletters());
    }
    @GetMapping("/{id}")
    public ResponseEntity<MonthlyNewsletter> getNewsletterById(@PathVariable Long id) {
        return monthlyNewsletterService.getNewsletterById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


}

