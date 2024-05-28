package com.example.ProiectFinalPs.Repository;


import com.example.ProiectFinalPs.Model.Magazin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MagazinRepository extends CrudRepository<Magazin, Integer> {

    Magazin findFirstByMagazinId(Integer id);
    void deleteById(Integer id);
}
