package com.example.cars.services;

import com.example.cars.entities.Reservation;
import java.util.List;

public interface IReservationService {
    Reservation addReservation(Reservation reservation);
    List<Reservation> getAllReservations();
    Reservation getReservationById(Long id);
    void deleteReservation(Long id);
}