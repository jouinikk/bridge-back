package com.example.cars.Repositories;

import com.example.cars.entities.LigneEau;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LigneEauRepository extends JpaRepository<LigneEau, Long> {
    List<LigneEau> findByPiscineId(Long piscineId);
    LigneEau findByPiscineIdAndNumero(Long piscineId, String numero);
}