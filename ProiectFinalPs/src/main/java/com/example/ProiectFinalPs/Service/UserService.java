package com.example.ProiectFinalPs.Service;

import com.example.ProiectFinalPs.Model.ParfumMagazin;
import com.example.ProiectFinalPs.Model.Utilizator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserService {

    Utilizator findFirstById(Integer id);

    List<Utilizator> findAll();

    public String insert(Utilizator utilizator);

    public void delete(Utilizator utilizator);

    public void deleteById(Integer id);

    public Utilizator login(Utilizator credentials);

    public Utilizator findByEmail(String email);

    public String saveAsCsv(Utilizator utilizator);

    public String saveAsDoc(Utilizator utilizator);

    public String saveAsJson(Utilizator utilizator);

    public String saveAsXml(Utilizator utilizator);

    public List<Utilizator> search(String userType);


}
