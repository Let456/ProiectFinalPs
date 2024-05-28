package com.example.ProiectFinalPs.Service;

import com.example.ProiectFinalPs.Model.Parfum;
import com.example.ProiectFinalPs.Model.Utilizator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ParfumService {

    Parfum findFirstById(Integer id);

    List<Parfum> findAll();

    public String insert(Parfum parfum);

    public void delete(Parfum parfum);

    public void deleteById(Integer id);


}
