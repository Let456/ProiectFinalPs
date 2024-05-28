package com.example.ProiectFinalPs.Controller;

import com.example.ProiectFinalPs.Model.Magazin;
import com.example.ProiectFinalPs.Model.ParfumMagazin;
import com.example.ProiectFinalPs.Service.Implementation.MagazinServiceImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/Magazin")
public class MagazinController {

    private final MagazinServiceImplementation magazinServiceImplementation;

    @GetMapping("/FindAll")
    public ResponseEntity<List<Magazin>> findAll()
    {
        List<Magazin> magazinList = magazinServiceImplementation.findAll();

        return ResponseEntity.ok(magazinList);
    }
}
