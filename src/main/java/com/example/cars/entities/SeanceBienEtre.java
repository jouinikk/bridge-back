package com.example.cars.entities;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;


@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SeanceBienEtre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Date date;

    int capacite;

    Integer duree;

    int placesDisponibles;

    @Enumerated(EnumType.STRING)
    TypeSeance type;

    String instructeur;

    String emailInstructeur;

    String niveau;

    boolean actif;
    @Column(name = "reminder_sent")
    private boolean reminderSent = false;

    @OneToMany(mappedBy = "seanceBienEtre")
    List<Reservation> reservations;
}

