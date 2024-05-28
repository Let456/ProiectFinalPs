package com.example.ProiectFinalPs.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data

public class Parfum{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer parfumId;

    private String nume;

    private String producator;
}
