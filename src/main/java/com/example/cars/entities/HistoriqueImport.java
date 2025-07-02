package com.example.cars.entities;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "HistoriqueImport")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HistoriqueImport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dateImport;

    @Column(nullable = false)
    private String typeImport; // exemple : "COMPETITION", "RESULTATS"

    @Column(length = 1000)
    private String details;

    @Column
    private String urlSource;

    @ManyToOne
    @JoinColumn(name = "competition_id")
    private Competition competition;


}
