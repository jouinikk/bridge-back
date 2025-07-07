package com.example.cars.entities;

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

    String firstName;
    String lastName;
    Integer age;
    String gender;
    String phone;
    String email;
    String category;
    String level;
    Integer sessionsPerWeek;
    @Temporal(TemporalType.DATE)
    Date registrationDate;
    Boolean actif;

    @JsonIgnoreProperties("nageurs")
    @ManyToMany(mappedBy = "nageurs")
    List<Groupe> groupes;
}
