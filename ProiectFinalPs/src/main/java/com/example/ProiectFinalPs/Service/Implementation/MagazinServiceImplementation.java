package com.example.ProiectFinalPs.Service.Implementation;

import com.example.ProiectFinalPs.Model.Magazin;
import com.example.ProiectFinalPs.Repository.MagazinRepository;
import com.example.ProiectFinalPs.Service.MagazinService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class MagazinServiceImplementation implements MagazinService {

    @Autowired
    private MagazinRepository magazinRepository;

    @Override
    public Magazin findFirstById(Integer id) {
        return magazinRepository.findFirstByMagazinId(id);
    }

    @Override
    public List<Magazin> findAll() {
        return (List<Magazin>) magazinRepository.findAll();
    }

    @Override
    public String insert(Magazin magazin) {
        return null;
    }

    @Override
    public void delete(Magazin magazin) {
        magazinRepository.delete(magazin);
    }

    @Override
    public void deleteById(Integer id) {
        magazinRepository.deleteById(id);
    }
}
