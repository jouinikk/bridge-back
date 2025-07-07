package com.example.cars.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "Competition")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Competition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String nom;

    @Column(nullable = false)
    LocalDate dateDebut;

    @Column
    LocalDate dateFin;

    @Column
    String lieu;

    @Column(length = 500)
    String description;

    @Column
    String urlSource;

    @Column
    private boolean estActive;

    @OneToMany(mappedBy = "competition", cascade = CascadeType.ALL)
    List<Resultat> resultats;

    @Column(nullable = true)
    private LocalDateTime dateImportation;

    @Column
    LocalDateTime lastModified;
    @Column(nullable = false)
    private boolean createdNotificationSent = false;

    @Column(nullable = false)
    private boolean updatedNotificationSent = false;

    public boolean isEstActive() {
        return estActive;
    }

    public void setEstActive(boolean estActive) {
        this.estActive = estActive;
    }

    public boolean isUpdatedNotificationSent() {
        return updatedNotificationSent;
    }
    public void setUpdatedNotificationSent(boolean updatedNotificationSent) {
        this.updatedNotificationSent = updatedNotificationSent;
    }

    public boolean isCreatedNotificationSent() {
        return createdNotificationSent;
    }
    public void setCreatedNotificationSent(boolean createdNotificationSent) {
        this.createdNotificationSent = createdNotificationSent;
    }
}