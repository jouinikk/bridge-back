package com.example.cars.Repositories;

import com.example.cars.entities.Cours;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourRepository extends JpaRepository<Cours,Integer> {
}
