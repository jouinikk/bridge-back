package com.example.cars.restcontrollers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.cars.entities.Reservation;
import com.example.cars.services.ReservationService;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        try {
            List<Reservation> reservations = reservationService.getAllReservations();
            return ResponseEntity.ok(reservations);
        } catch (Exception e) {
            log.error("Error getting all reservations", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Reservation> addReservation(@RequestBody Reservation reservation) {
        try {
            log.info("Received reservation request: {}", reservation);

            // Log the IDs for debugging
            log.info("Nageur ID: {}", reservation.getNageur() != null ? reservation.getNageur().getId() : "null");
            log.info("SeanceBienEtre ID: {}", reservation.getSeanceBienEtre() != null ? reservation.getSeanceBienEtre().getId() : "null");

            Reservation savedReservation = reservationService.addReservation(reservation);

            log.info("Reservation saved successfully: {}", savedReservation);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedReservation);
        } catch (Exception e) {
            log.error("Error adding reservation", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        try {
            Reservation reservation = reservationService.getReservationById(id);
            return reservation != null ? ResponseEntity.ok(reservation) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error getting reservation by id: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        try {
            reservationService.deleteReservation(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error deleting reservation with id: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/swimmer/{swimmerId}")
    public ResponseEntity<List<Reservation>> getReservationsBySwimmerId(@PathVariable Long swimmerId) {
        try {
            List<Reservation> reservations = reservationService.getReservationsBySwimmerId(swimmerId);
            return ResponseEntity.ok(reservations);
        } catch (Exception e) {
            log.error("Error getting reservations for swimmer: {}", swimmerId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}