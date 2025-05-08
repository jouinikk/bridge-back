package com.example.cars.restcontrollers;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.example.cars.entities.Reservation;
import com.example.cars.services.ReservationService;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public Reservation add(@RequestBody Reservation reservation) {
        return reservationService.addReservation(reservation);
    }

    @GetMapping
    public List<Reservation> getAll() {
        return reservationService.getAllReservations();
    }

    @GetMapping("/{id}")
    public Reservation getById(@PathVariable Long id) {
        return reservationService.getReservationById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        reservationService.deleteReservation(id);
    }
}
