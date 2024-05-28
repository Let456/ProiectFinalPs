package com.example.ProiectFinalPs.Repository;

import com.example.ProiectFinalPs.Model.SticlaParfum;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SticlaParfumRepository extends CrudRepository<SticlaParfum, Integer> {

    SticlaParfum findFirstBySticlaParfumId(Integer id);
}
