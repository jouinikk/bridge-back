package com.example.cars.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "Resultat")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Resultat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String nomNageur;

    @Column(nullable = false)
    String epreuve;

    @Column(nullable = false)
    String temps;

    @Column
    String club;

    @ManyToOne
    @JoinColumn(name = "competition_id")
    Competition competition;

    @Column
    String categorie;

    @Column
    int rang;

}
