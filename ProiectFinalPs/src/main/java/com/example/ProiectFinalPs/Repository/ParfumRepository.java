package com.example.ProiectFinalPs.Repository;


import com.example.ProiectFinalPs.Model.Parfum;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParfumRepository extends CrudRepository<Parfum, Integer> {

    Parfum findFirstByParfumId(Integer id);
    void deleteByParfumId(Integer id);

}
