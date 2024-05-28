package com.example.ProiectFinalPs.Repository;

import com.example.ProiectFinalPs.Model.ParfumMagazin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParfumMagazinRepository extends CrudRepository<ParfumMagazin, Integer> {

    ParfumMagazin findFirstByParfumMagazinId(Integer id);
}
