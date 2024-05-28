package com.example.ProiectFinalPs.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data

public class ParfumMagazin{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer parfumMagazinId;

    private Integer disponibilitate;

    @OneToOne
    private SticlaParfum sticlaParfum;

    @ManyToOne
    @JoinColumn(name = "magazinId")
    private Magazin magazin;

}
