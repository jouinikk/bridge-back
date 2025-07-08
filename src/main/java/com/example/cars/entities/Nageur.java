package com.example.cars.entities;

import com.example.cars.entities.Groupe;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class Nageur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Champs communs
    String nom;
    String prenom;
    String email;
    String phone;

    // Champs supplémentaires
    Integer age;
    String gender;
    String category;
    String level;
    String firstName;
    String lastName;

    String niveau;
    Integer sessionsPerWeek;

    @Temporal(TemporalType.DATE)
    Date registrationDate;

    Boolean actif;

    @JsonIgnoreProperties("nageurs")
    @ManyToMany(mappedBy = "nageurs")
    List<Groupe> groupes;
}
