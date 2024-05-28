package com.example.ProiectFinalPs.Service;

import com.example.ProiectFinalPs.Model.SticlaParfum;
import com.example.ProiectFinalPs.Model.Utilizator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SticlaParfumService {

    SticlaParfum findFirstById(Integer id);

    List<SticlaParfum> findAll();

    public String insert(SticlaParfum sticlaParfum);

    public void delete(SticlaParfum sticlaParfum);

    public void deleteById(Integer id);


}
