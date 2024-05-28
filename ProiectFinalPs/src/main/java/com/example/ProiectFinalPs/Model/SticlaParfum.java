package com.example.ProiectFinalPs.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SticlaParfum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sticlaParfumId;

    @OneToOne
    private Parfum parfum;

    private Integer volum;

    private Integer pret;
}
