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
public class Groupe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nom;
    private String niveau; // Beginner, Intermediate, Advanced, etc.
    private int nombreMaxNageurs;
    
    @ManyToOne
    @JoinColumn(name = "coach_id")
    private Coach coach;
    
    @ManyToMany
    @JoinTable(
        name = "groupe_nageur",
        joinColumns = @JoinColumn(name = "groupe_id"),
        inverseJoinColumns = @JoinColumn(name = "nageur_id")
    )
    private List<Nageur> nageurs;
    
    @OneToMany(mappedBy = "groupe")
    private List<Seance> seances;
}