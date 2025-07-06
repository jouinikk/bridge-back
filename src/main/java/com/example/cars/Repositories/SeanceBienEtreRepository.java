package com.example.cars.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import com.example.cars.entities.SeanceBienEtre;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface SeanceBienEtreRepository extends JpaRepository<SeanceBienEtre, Long> {
    @Query("SELECT s FROM SeanceBienEtre s WHERE s.reminderSent = false AND s.date BETWEEN :start AND :end")
    List<SeanceBienEtre> findSeancesForReminder(@Param("start") Date start, @Param("end") Date end);
}

