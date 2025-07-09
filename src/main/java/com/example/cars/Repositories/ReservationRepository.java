package com.example.cars.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.cars.entities.Reservation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
        List<Reservation> findByNageurLastNameContainingIgnoreCase(String lastName);
        List<Reservation> findByNageurId(Long nageurId);
    @Query("SELECT r FROM Reservation r WHERE r.nageur.id = :swimmerId")
    List<Reservation> findBySwimmerId(@Param("swimmerId") Long swimmerId);
}