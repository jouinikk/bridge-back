package com.example.cars.Repositories;
import com.example.cars.entities.Resultat;
import com.example.cars.entities.Competition;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ResultatRepository extends JpaRepository<Resultat, Long> {

    Resultat findById(long id);
    List<Resultat> findByEpreuve(String epreuve);
    List<Resultat> findByCompetition(Competition competition);
    List<Resultat> findByNomNageurContainingIgnoreCase(String nom);
    List<Resultat> findByClub(String club);
}