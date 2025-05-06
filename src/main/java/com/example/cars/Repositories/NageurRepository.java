package com.example.cars.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import com.example.cars.entities.Nageur;

public interface NageurRepository extends JpaRepository<Nageur, Long> {
}
