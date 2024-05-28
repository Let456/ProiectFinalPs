package com.example.ProiectFinalPs.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data

public class Utilizator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userType;

    private String nume;

    private String parola;

    private String email;

    @ManyToOne
    @JoinColumn(name = "magazinId")
    private Magazin magazin;

}
