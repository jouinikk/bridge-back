package com.example.cars.services;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.cars.entities.Reservation;
import com.example.cars.Repositories.ReservationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService implements IReservationService {
    private final ReservationRepository reservationRepo;

    public Reservation addReservation(Reservation reservation) {
        return reservationRepo.save(reservation);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepo.findAll();
    }

    public Reservation getReservationById(Long id) {
        return reservationRepo.findById(id).orElse(null);
    }

    public void deleteReservation(Long id) {
        reservationRepo.deleteById(id);
    }
}
