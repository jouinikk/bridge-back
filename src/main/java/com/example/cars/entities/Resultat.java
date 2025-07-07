package com.example.cars.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import com.fasterxml.jackson.annotation.JsonProperty;

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

    @JsonProperty("Place")
    String place;

    @JsonProperty("Nom et prénom")
    String nomNageur;

    @JsonProperty("Nation")
    String nation;

    @JsonProperty("Naissance")
    String naissance;

    @JsonProperty("Club")
    String club;

    @JsonProperty("Temps")
    String temps;

    @JsonProperty("Points")
    String points;

    @JsonProperty("Temps de passage")
    String tempsDePassage;

    // You might still need to associate this Resultat with a Competition
    @ManyToOne
    @JoinColumn(name = "competition_id")
    Competition competition;

    // You might want to keep or remove these depending on your needs
   // String categorie;
   // Integer classement;
   // Integer rang;
   // String epreuve; // You'll likely need to determine the event from context
}