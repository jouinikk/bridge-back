package com.example.cars.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import com.example.cars.entities.Repas;

public interface RepasRepository extends JpaRepository<Repas, Long> {
}