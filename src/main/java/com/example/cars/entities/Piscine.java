// package com.example.cars.entities;

// import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
// import jakarta.persistence.*;
// import lombok.*;
// import lombok.experimental.FieldDefaults;

// import java.util.ArrayList;
// import java.util.List;

// @Entity
// @Getter
// @Setter
// @ToString // Exclude complex objects from toString
// @NoArgsConstructor
// @AllArgsConstructor
// @Builder
// @FieldDefaults(level = AccessLevel.PRIVATE)
// public class Piscine {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;
    
//     private String nom;
//     private String adresse;
//     private String ville;
//     private String codePostal;
//     private String telephone;
//     private String email;
//     @Column(name = "nombre_lignes_eau")
//     private int nombreLignesEau;
    
//     @PrePersist
//     @PreUpdate
//     private void updateLigneCount() {
//         this.nombreLignesEau = this.lignesEau != null ? this.lignesEau.size() : 0;
//     }
//     // private int nombreLignesEau; // Number of swimming lanes
//     @Transient  // This makes it non-persistent
//     public int getNombreLignesEau() {
//         return this.lignesEau != null ? this.lignesEau.size() : 0;
//     }
//     // Geolocation data for map integration
//     private double latitude;
//     private double longitude;
    
  

//     @JsonIgnoreProperties({"piscine", "seances"})
//     @OneToMany(mappedBy = "piscine", cascade = CascadeType.ALL, orphanRemoval = true)
//     @ToString.Exclude
//     private List<LigneEau> lignesEau;
    
//     @ManyToOne
//     @JoinColumn(name = "centre_id")
//     private Centre centre;
    
//     // Helper methods to manage lignesEau
//     public void addLigneEau(LigneEau ligne) {
//         if (lignesEau == null) {
//             lignesEau = new ArrayList<>();
//         }
//         lignesEau.add(ligne);
//         ligne.setPiscine(this);
//     }
    
//     public void removeLigneEau(LigneEau ligne) {
//         if (lignesEau != null) {
//             lignesEau.remove(ligne);
//             ligne.setPiscine(null);
//         }
//     }
// }









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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Add this

public class Piscine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nom;
    private String adresse;
    private String ville;
    private String codePostal;
    private String telephone;
    private String email;
    
    // This field is calculated automatically and persisted
    @Column(name = "nombre_lignes_eau")
    private int nombreLignesEau;
    
    // Geolocation data for map integration
    private double latitude;
    private double longitude;
    
    @JsonIgnoreProperties({"piscine", "seances"})
    @OneToMany(mappedBy = "piscine", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @Builder.Default // Ensures the list is initialized when using builder
    private List<LigneEau> lignesEau = new ArrayList<>();
    
    @ManyToOne
    @JoinColumn(name = "centre_id")
    private Centre centre;
    
    // This callback updates the count before persisting or updating
    @PrePersist
    // @PreUpdate
    @PostLoad
    // private void updateLigneCount() {
    //     this.nombreLignesEau = this.lignesEau.size();
    // }
    private void updateLigneCount() {
        this.nombreLignesEau = (this.lignesEau != null) ? this.lignesEau.size() : 0;
    }
    // Helper methods to manage lignesEau
    public void addLigneEau(LigneEau ligne) {
        if (ligne != null) {
            if (this.lignesEau == null) {
                this.lignesEau = new ArrayList<>();
            }
            ligne.setPiscine(this);
            this.lignesEau.add(ligne);
            this.nombreLignesEau = this.lignesEau.size();
        }
    }
    
    public void removeLigneEau(LigneEau ligne) {
        if (ligne != null && this.lignesEau != null) {
            this.lignesEau.remove(ligne);
            ligne.setPiscine(null);
            this.nombreLignesEau = this.lignesEau.size();
        }
    }
}