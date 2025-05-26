package com.example.cars.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Nageur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private int age;
    private String sexe;
    private String email;
    @OneToMany(mappedBy = "nageur")
    private List<EvaluationSante> evaluations;

    @OneToMany(mappedBy = "nageur")
    private List<Reservation> reservations;

    // Getters/Setters
}