package com.example.demo.services.impl;

import com.example.demo.entities.Sports;
import com.example.demo.exceptions.SportsNotFoundException;
import com.example.demo.repositories.SportsRepository;
import com.example.demo.services.SportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class SportsServiceImpl implements SportsService {
    @Autowired
    private SportsRepository sportsRepository;


    public Sports getSportsByName(String sportName) {
        Sports sports = sportsRepository.findByName(sportName);
        if (sports == null) {
            throw new SportsNotFoundException("Sport with name " + sportName + " not found.");
        }
        return sports;
    }

    @Transactional
    public void deleteSport(Long sportId) {
        sportsRepository.deleteById(sportId);
    }

    public Sports createSports(Sports sports) {
        return sportsRepository.save(sports);
    }

    public Sports updateSports( Sports sports) {
        if (sportsRepository.existsById(sports.getId())) {
            return sportsRepository.save(sports);
        } else {
            throw new EntityNotFoundException("Sports not found with ID: " + sports.getId());
        }
    }

    public void deleteSports(Long id) {
        sportsRepository.deleteById(id);
    }

    public Page<Sports> getAllSports(Pageable pageable) {
        return sportsRepository.findAll(pageable);
    }

    public Sports getSportsById(Long id) {
        return sportsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sports not found with ID: " + id));
    }
}
