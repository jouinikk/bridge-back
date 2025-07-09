package com.example.cars.Repositories;

import com.example.cars.entities.Nageur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NageurRepository extends JpaRepository<Nageur, Long> {
    List<Nageur> findByLevel(String level);
    List<Nageur> findByGroupesId(Long groupeId);
}