package com.example.ProiectFinalPs.Repository;


import com.example.ProiectFinalPs.Model.Utilizator;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<Utilizator, Integer> {

    Utilizator findFirstById(Integer id);

    Utilizator findFirstByEmail(String email);
    void deleteById(Integer id);
}
