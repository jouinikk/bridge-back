package com.example.cars.Repositories;

import com.example.cars.entities.Piscine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PiscineRepository extends JpaRepository<Piscine, Long> {
    List<Piscine> findByVille(String ville);
    List<Piscine> findByNomContaining(String keyword);
}