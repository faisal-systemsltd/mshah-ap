package com.example.demo.services;

import com.example.demo.entities.Sports;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.util.List;


public interface SportsService {

    public Sports getSportsByName(String sportName) ;

    @Transactional
    public void deleteSport(Long sportId) ;

    public Sports createSports(Sports sports);

    public Sports updateSports( Sports sports) ;

    public void deleteSports(Long id);

    public Page<Sports> getAllSports(Pageable pageable);
    public Sports getSportsById(Long id);

}

