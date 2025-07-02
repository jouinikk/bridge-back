package com.example.cars.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    private Long id;
    
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String niveau; // Beginner, Intermediate, Advanced, etc.
    
    @JsonIgnoreProperties("nageurs")
    @ManyToMany(mappedBy = "nageurs")
    private List<Groupe> groupes;
}
