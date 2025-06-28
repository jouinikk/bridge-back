package com.example.cars.Repositories;

import com.example.cars.entities.Groupe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupeRepository extends JpaRepository<Groupe, Long> {
    List<Groupe> findByCoachId(Long coachId);
}