package com.example.ProiectFinalPs.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data

public class Magazin{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer magazinId;

    @OneToMany
    private List<Utilizator> angajati;

    @OneToMany
    private List<ParfumMagazin> parfumMagazine;
}
