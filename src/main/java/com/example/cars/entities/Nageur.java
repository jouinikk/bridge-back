package com.example.cars.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
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
    Long id;

    // Correspond à firstName dans TS
    String firstName;

    // Correspond à lastName dans TS
    String lastName;

    int age;

    // Correspond à gender dans TS
    String gender;

    String phone;

    String email;

    String category;

    String level;

    // Correspond à sessionsPerWeek dans TS
    int sessionsPerWeek;

    // Correspond à registrationDate dans TS
    LocalDate registrationDate;
    @OneToMany(mappedBy = "nageur")
    @JsonManagedReference
    private List<EvaluationSante> evaluations;

    @OneToMany(mappedBy = "nageur")
    @JsonManagedReference
    private List<Reservation> reservations;
}
