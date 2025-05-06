package com.example.cars.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.cars.entities.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}