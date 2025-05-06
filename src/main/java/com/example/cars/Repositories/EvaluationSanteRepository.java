package com.example.cars.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import com.example.cars.entities.EvaluationSante;

public interface EvaluationSanteRepository extends JpaRepository<EvaluationSante, Long> {
}
