package com.example.cars.Repositories;

import com.example.cars.entities.MonthlyNewsletter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.YearMonth;
import java.util.Optional;

public interface MonthlyNewsletterRepository extends JpaRepository<MonthlyNewsletter, Long> {
    Optional<MonthlyNewsletter> findByMonth(YearMonth month);
}
