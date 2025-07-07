package com.example.cars.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

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

    @ManyToOne
    @JoinColumn(name = "nageur_id", referencedColumnName = "id")
    private Nageur nageur;


    @ManyToOne
    @JoinColumn(name = "seance_bien_etre_id", referencedColumnName = "id")
    @JsonIgnore
    private SeanceBienEtre seanceBienEtre;

    private Date dateReservation;
}
