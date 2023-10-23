package com.example.demo.controllers;

import com.example.demo.dtos.PlayerDTO;
import com.example.demo.dtos.SportsResponseDTO;
import com.example.demo.entities.Player;
import com.example.demo.entities.Sports;
import com.example.demo.exceptions.SportsNotFoundException;
import com.example.demo.services.SportsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sports")
public class SportsController {

    @Autowired
    private SportsService sportsService;

    @GetMapping
    public ResponseEntity<List<SportsResponseDTO>> getSportsByNames(
            @RequestParam("names") List<String> sportNames) {
        List<SportsResponseDTO> sportsResponseList = new ArrayList<>();

        for (String sportName : sportNames) {
            try {
                Sports sports = sportsService.getSportsByName(sportName);
                List<PlayerDTO> players = sports.getPlayers()
                        .stream()
                        .map(this::mapPlayerToDTO)
                        .collect(Collectors.toList());

                SportsResponseDTO responseDTO = new SportsResponseDTO(sportName, players);
                sportsResponseList.add(responseDTO);
            } catch (SportsNotFoundException e) {
                // Handle case when a sport with the given name is not found
            }
        }

        return new ResponseEntity<>(sportsResponseList, HttpStatus.OK);
    }

    @DeleteMapping("/{sportId}")
    public ResponseEntity<Void> deleteSport(@PathVariable Long sportId) {
        sportsService.deleteSport(sportId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private PlayerDTO mapPlayerToDTO(Player player) {
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setPlayerName(player.getName());
        playerDTO.setAge(player.getAge());
        playerDTO.setLevel(player.getLevel());
        playerDTO.setEmail(player.getEmail());
        playerDTO.setGender(player.getGender().toString());

        return playerDTO;
    }

    //CRUD

    @PostMapping
    public ResponseEntity<Sports> createSports(@RequestBody Sports sports) {
        Sports createdSports = sportsService.createSports(sports);
        return new ResponseEntity<>(createdSports, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sports> updateSports(@PathVariable Long id, @RequestBody Sports sports) {
        Sports updatedSports = sportsService.updateSports(id, sports);
        return new ResponseEntity<>(updatedSports, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSports(@PathVariable Long id) {
        sportsService.deleteSports(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<Sports>> getAllSports() {
        List<Sports> sportsList = sportsService.getAllSports();
        return new ResponseEntity<>(sportsList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sports> getSportsById(@PathVariable Long id) {
        Sports sports = sportsService.getSportsById(id);
        return new ResponseEntity<>(sports, HttpStatus.OK);
    }
}
