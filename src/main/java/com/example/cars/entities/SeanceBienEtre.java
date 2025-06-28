package com.example.cars.entities;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SeanceBienEtre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    private int capacite;

    private Integer duree; // Durée en minutes

    private int placesDisponibles;

    private String instructeur;

    private String emailInstructeur;

    private String niveau;
    Boolean   actif;

    @Enumerated(EnumType.STRING)
    private TypeSeance type;

    @OneToMany(mappedBy = "seanceBienEtre", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reservation> reservations;
}
