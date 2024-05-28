package com.example.ProiectFinalPs.Controller;

import com.example.ProiectFinalPs.DTO.NameRequest;
import com.example.ProiectFinalPs.Model.*;
import com.example.ProiectFinalPs.Service.Implementation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/ParfumMagazin")
public class ParfumMagazinController {

    private final ParfumMagazinServiceImplementation parfumMagazinServiceImplementation;

    private final UserServiceImplementation userServiceImplementation;

    private final ParfumServiceImplementation parfumServiceImplementation;

    private final SticlaParfumServiceImplementation sticlaParfumServiceImplementation;

    private final MagazinServiceImplementation magazinServiceImplementation;

    @GetMapping("/FindAll")
    public ResponseEntity<List<ParfumMagazin>> findAll()
    {
        List<ParfumMagazin> pcList = parfumMagazinServiceImplementation.findAll();

        return ResponseEntity.ok(pcList);
    }

    @PostMapping("/FindAllFrom")
    public ResponseEntity<List<ParfumMagazin>> findAllFrom(@RequestBody Integer id)
    {
        Utilizator user =  userServiceImplementation.findFirstById(id);
        List<ParfumMagazin> parfumMagazinList = parfumMagazinServiceImplementation.findAllFrom(user.getMagazin().getMagazinId());

        return ResponseEntity.ok(parfumMagazinList);
    }

    @PostMapping("/FindAllFromMagazin")
    public ResponseEntity<List<ParfumMagazin>> findAllFromMagazin(@RequestBody Integer id)
    {
        List<ParfumMagazin> parfumMagazinList = parfumMagazinServiceImplementation.findAllFrom(id);
        return ResponseEntity.ok(parfumMagazinList);
    }

    @PostMapping("/Insert")
    public ResponseEntity<String> Insert(@RequestBody ParfumMagazin parfumMagazin)
    {
        System.out.println(parfumMagazin);
        SticlaParfum sticlaParfum = parfumMagazin.getSticlaParfum();
        Parfum parfum = sticlaParfum.getParfum();
        //Magazin magazin = magazinServiceImplementation.findFirstById(parfumMagazin.getParfumMagazinId());
        //parfumMagazin.setMagazin(magazin);
        parfumServiceImplementation.insert(parfum);
        sticlaParfumServiceImplementation.insert(sticlaParfum);
        parfumMagazinServiceImplementation.insert(parfumMagazin);

        return ResponseEntity.ok("Inserted");
    }


    @PostMapping("/Delete")
    public ResponseEntity<String> delete(@RequestBody ParfumMagazin parfumMagazin)
    {
        SticlaParfum sticlaParfum = parfumMagazin.getSticlaParfum();
        Parfum parfum = sticlaParfum.getParfum();
        parfumMagazinServiceImplementation.delete(parfumMagazin);
        sticlaParfumServiceImplementation.delete(sticlaParfum);
        parfumServiceImplementation.delete(parfum);

        return ResponseEntity.ok("deleted");
    }

    @PostMapping("/Search")
    public ResponseEntity<List<ParfumMagazin>> search(@RequestBody NameRequest nameRequest)
    {
        System.out.println(nameRequest.getName());
     List<ParfumMagazin> parfumMagazins = parfumMagazinServiceImplementation.search(nameRequest.getName());
     return ResponseEntity.ok(parfumMagazins);
    }
}
