package com.example.cars.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LigneEau {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String numero; // Identification of the swimming lane
    private String description;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "piscine_id")
    @JsonIgnoreProperties({"lignesEau", "seances"})
    private Piscine piscine;
    
    // @OneToMany(mappedBy = "ligneEau")
    // @JsonIgnoreProperties({"ligneEau", "coach", "groupe", "piscine"})
    // @ToString.Exclude
    // private List<Seance> seances;
    @OneToMany(mappedBy = "ligneEau", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({ "ligneEau", "coach", "groupe", "piscine" })
    @ToString.Exclude
    private List<Seance> seances;
}