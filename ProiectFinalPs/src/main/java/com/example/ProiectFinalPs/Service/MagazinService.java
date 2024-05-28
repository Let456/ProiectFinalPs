package com.example.ProiectFinalPs.Service;


import com.example.ProiectFinalPs.Model.Magazin;
import com.example.ProiectFinalPs.Model.Utilizator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MagazinService {

    Magazin findFirstById(Integer id);

    List<Magazin> findAll();

    public String insert(Magazin magazin);

    public void delete(Magazin magazin);

    public void deleteById(Integer id);


}
