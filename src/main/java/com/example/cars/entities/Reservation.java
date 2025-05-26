package com.example.cars.entities;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Date;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dateReservation;
    @ManyToOne
    @JoinColumn(name = "nageur_id", referencedColumnName = "id")
    private Nageur nageur;

    @ManyToOne
    @JoinColumn(name = "seance_bien_etre_id", referencedColumnName = "id")
    private SeanceBienEtre seanceBienEtre;


}
