package com.example.cars.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class EvaluationSante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date;
    private int niveauStress;
    private int motivation;
    private int humeur;
    @ManyToOne
    @JsonBackReference

    @JoinColumn(name = "nageur_id")
    private Nageur nageur;

}
