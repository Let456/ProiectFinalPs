package com.example.ProiectFinalPs.Service.Implementation;

import com.example.ProiectFinalPs.Model.SticlaParfum;
import com.example.ProiectFinalPs.Repository.SticlaParfumRepository;
import com.example.ProiectFinalPs.Service.SticlaParfumService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SticlaParfumServiceImplementation implements SticlaParfumService {

    @Autowired
    private SticlaParfumRepository sticlaParfumRepository;

    @Override
    public SticlaParfum findFirstById(Integer id) {
        return sticlaParfumRepository.findFirstBySticlaParfumId(id);
    }

    @Override
    public List<SticlaParfum> findAll() {
        return (List<SticlaParfum>) sticlaParfumRepository.findAll();
    }

    @Override
    public String insert(SticlaParfum sticlaParfum) {
        sticlaParfumRepository.save(sticlaParfum);
        return ("Saved");
    }

    @Override
    public void delete(SticlaParfum sticlaParfum) {
        sticlaParfumRepository.delete(sticlaParfum);
    }

    @Override
    public void deleteById(Integer id) {
        sticlaParfumRepository.deleteById(id);
    }
}
