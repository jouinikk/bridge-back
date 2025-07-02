package com.example.cars.Repositories;
import com.example.cars.entities.LigneScrapee;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface LigneScrapeeRepository extends JpaRepository<LigneScrapee, Long> {

    LigneScrapee findById(long id);
    List<LigneScrapee> findByUrlSource(String urlSource);
    List<LigneScrapee> findByDateScrapeAfter(java.time.LocalDateTime date);
}