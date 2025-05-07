package com.example.cars.Repositories;

import com.example.cars.entities.Coach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoachRepository extends JpaRepository<Coach, Long> {
    List<Coach> findBySpecialite(String specialite);
}