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
public class Groupe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nom;
    private String niveau; // Beginner, Intermediate, Advanced, etc.
    private int nombreMaxNageurs;
    
    @JsonIgnoreProperties({"groupes", "seances"})
    @ManyToOne
    @JoinColumn(name = "coach_id")
    @ToString.Exclude
    private Coach coach;
    
    @JsonIgnoreProperties("groupes")
    @ManyToMany
    @JoinTable(
        name = "groupe_nageur",
        joinColumns = @JoinColumn(name = "groupe_id"),
        inverseJoinColumns = @JoinColumn(name = "nageur_id")
    )
    @ToString.Exclude
    private List<Nageur> nageurs;
    
    @JsonIgnoreProperties({"groupe", "coach"})
    @OneToMany(mappedBy = "groupe")
    @ToString.Exclude
    private List<Seance> seances;
}