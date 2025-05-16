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
public class LigneEau {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String numero; // Identification of the swimming lane
    private String description;
    
    @JsonIgnoreProperties("lignesEau")
    @ManyToOne
    @JoinColumn(name = "piscine_id")
    private Piscine piscine;
    
    @JsonIgnoreProperties({"ligneEau", "coach", "groupe"})
    @OneToMany(mappedBy = "ligneEau")
    private List<Seance> seances;
}