package com.example.cars.entities;
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
public class SeanceBienEtre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date;

    private int capacite;

    @Enumerated(EnumType.STRING)
    private TypeSeance type;
    @OneToMany(mappedBy = "seanceBienEtre")
    private List<Reservation> reservations;
}
