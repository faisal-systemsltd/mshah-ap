package com.example.demo;


import com.example.demo.dtos.CreateSportsRequestDTO;
import com.example.demo.entities.Sports;
import com.example.demo.repositories.SportsRepository;
import com.example.demo.services.impl.SportsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class SportsServiceTest {

    @Mock
    private SportsRepository sportRepository;

    @InjectMocks
    private SportsServiceImpl sportService;

    @BeforeEach
    public void setUp() {
        // Define a sample sport for testing
        Sports sport = new Sports();
        sport.setId(1L);
        sport.setName("Soccer");
        sport.setDescription("A popular sport");

        // Stub the repository methods
        lenient().when(sportRepository.findById(1L)).thenReturn(Optional.of(sport));
        lenient().when(sportRepository.save(Mockito.any(Sports.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    public void testGetSport() {
        Long sportId = 1L;

        Sports sport = sportService.getSportsById(sportId);

        assertNotNull(sport);
        assertEquals("Soccer", sport.getName());
        assertEquals("A popular sport", sport.getDescription());
    }

    @Test
    public void testCreateSport() {
        Sports createRequest = new Sports();
        createRequest.setName("Tennis");
        createRequest.setDescription("A racket sport");

        Sports createdSport = sportService.createSports(createRequest);

        assertNotNull(createdSport);
        assertEquals("Tennis", createdSport.getName());
        assertEquals("A racket sport", createdSport.getDescription());
    }


    @Test
    public void testGetAllSports() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Sports> sportsPage = new PageImpl<>(Collections.singletonList(new Sports()));

        lenient().when(sportRepository.findAll(pageable)).thenReturn(sportsPage);

        Page<Sports> result = (Page<Sports>) sportService.getAllSports(pageable);

        assertNotNull(result);
    }
}

