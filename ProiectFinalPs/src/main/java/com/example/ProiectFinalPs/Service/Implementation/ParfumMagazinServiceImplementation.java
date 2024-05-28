package com.example.ProiectFinalPs.Service.Implementation;

import com.example.ProiectFinalPs.Model.ParfumMagazin;
import com.example.ProiectFinalPs.Repository.ParfumMagazinRepository;
import com.example.ProiectFinalPs.Service.ParfumMagazinService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParfumMagazinServiceImplementation implements ParfumMagazinService {

    @Autowired
    private ParfumMagazinRepository parfumMagazinRepository;

    @Override
    public ParfumMagazin findFirstById(Integer id) {
        return parfumMagazinRepository.findFirstByParfumMagazinId(id);
    }

    @Override
    public List<ParfumMagazin> findAll() {
        return (List<ParfumMagazin>) parfumMagazinRepository.findAll();
    }

    @Override
    public List<ParfumMagazin> findAllFrom(Integer id) {
        List<ParfumMagazin> all = findAll();
        List<ParfumMagazin> finalList = new ArrayList<>();

        for(ParfumMagazin parfumMagazin : all){
            if(parfumMagazin.getMagazin().getMagazinId() == id){
                finalList.add(parfumMagazin);
            }
        }

        finalList = finalList.stream()
                .sorted(Comparator.comparing((ParfumMagazin pm) -> pm.getSticlaParfum().getParfum().getNume())
                        .thenComparing(pm -> pm.getSticlaParfum().getPret()))
                .collect(Collectors.toList());

        return finalList;
    }

    @Override
    public String insert(ParfumMagazin parfumMagazin) {
        parfumMagazinRepository.save(parfumMagazin);
        return ("Saved");
    }

    @Override
    public void delete(ParfumMagazin parfumMagazin) {
        parfumMagazinRepository.delete(parfumMagazin);
    }

    @Override
    public void deleteById(Integer id) {
        parfumMagazinRepository.deleteById(id);
    }

    @Override
    public List<ParfumMagazin> search(String nume) {

        List<ParfumMagazin> parfumMagazinList = (List<ParfumMagazin>) parfumMagazinRepository.findAll();
        List<ParfumMagazin> filteredParfumMagazinList = new ArrayList<>();

        for(ParfumMagazin parfumMagazin:parfumMagazinList)
        {
            if(parfumMagazin.getSticlaParfum().getParfum().getNume().equals(nume))
            {
                filteredParfumMagazinList.add(parfumMagazin);
            }
        }

        return filteredParfumMagazinList;

    }
}
