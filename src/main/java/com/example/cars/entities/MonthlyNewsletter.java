package com.example.cars.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.time.YearMonth;

@Entity
@Table(name = "NewsLetter")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MonthlyNewsletter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private YearMonth month; // e.g. 2025-06
    private LocalDateTime sentAt;

    @Column(columnDefinition = "TEXT")
    private String contentHtml;

    @Column(length = 255)
    private String tipOfTheMonth;

    private int competitionsCount;
    private int resultsCount;
}
