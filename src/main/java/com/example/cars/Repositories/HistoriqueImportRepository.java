package com.example.cars.Repositories;
import com.example.cars.entities.HistoriqueImport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface HistoriqueImportRepository extends JpaRepository<HistoriqueImport, Long> {

    HistoriqueImport findById(long id);
    List<HistoriqueImport> findAllByOrderByIdDesc();
    List<HistoriqueImport> findAllByOrderByIdAsc();
    List<HistoriqueImport> findByDateImport(LocalDate date);
    List<HistoriqueImport> findByTypeImport(String typeImport);
    List<HistoriqueImport> findByUrlSourceContaining(String keyword);
}