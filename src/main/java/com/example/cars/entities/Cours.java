package com.example.cars.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Table
@RequiredArgsConstructor
@Entity
@Getter
@Setter
public class Cours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String imageUrl;
    private float price;

}