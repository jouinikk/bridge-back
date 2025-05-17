package com.example.cars.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "Competition")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Competition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String nom;

    @Column(nullable = false)
    LocalDate dateDebut;

    @Column
    LocalDate dateFin;

    @Column
    String lieu;

    @Column(length = 500)
    String description;

    @Column
    String urlSource;

    @Column
    boolean estActive;

    @OneToMany(mappedBy = "competition", cascade = CascadeType.ALL)
    List<Resultat> resultats;

    @Column
    LocalDateTime dateImportation;

}