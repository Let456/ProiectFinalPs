package com.example.ProiectFinalPs.Service;

import com.example.ProiectFinalPs.Model.ParfumMagazin;
import com.example.ProiectFinalPs.Model.Utilizator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ParfumMagazinService {

    ParfumMagazin findFirstById(Integer id);

    List<ParfumMagazin> findAll();

    List<ParfumMagazin> findAllFrom(Integer id);

    public String insert(ParfumMagazin parfumMagazin);

    public void delete(ParfumMagazin parfumMagazin);

    public void deleteById(Integer id);

    public List<ParfumMagazin> search(String nume);

}
