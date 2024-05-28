package com.example.ProiectFinalPs.Service.Implementation;

import com.example.ProiectFinalPs.Model.Parfum;
import com.example.ProiectFinalPs.Repository.ParfumRepository;
import com.example.ProiectFinalPs.Service.ParfumService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParfumServiceImplementation implements ParfumService {

    @Autowired
    private ParfumRepository parfumRepository;

    @Override
    public Parfum findFirstById(Integer id) {
        return parfumRepository.findFirstByParfumId(id);
    }

    @Override
    public List<Parfum> findAll() {
        return (List<Parfum>) parfumRepository.findAll();
    }

    @Override
    public String insert(Parfum parfum) {
        parfumRepository.save(parfum);
        return ("Saved");
    }

    @Override
    public void delete(Parfum parfum) {
        parfumRepository.delete(parfum);
    }

    @Override
    public void deleteById(Integer id) {
        parfumRepository.deleteById(id);
    }
}
