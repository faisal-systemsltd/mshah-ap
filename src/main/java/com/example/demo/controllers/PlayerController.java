package com.example.demo.controllers;

import com.example.demo.dtos.PlayerDTO;
import com.example.demo.dtos.UpdatePlayerRequestDTO;
import com.example.demo.dtos.UpdatePlayerSportsRequestDTO;
import com.example.demo.entities.Player;
import com.example.demo.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @GetMapping("/players-by-category")
    public ResponseEntity<Page<PlayerDTO>> getPlayersBySportsCategory(
            @RequestParam("category") String category,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Player> playersPage = playerService.getPlayersBySportsCategory(category, pageable);

        // Map the Player entities to PlayerDTOs
        Page<PlayerDTO> playerDTOs = playersPage.map(this::mapPlayerToDTO);

        return new ResponseEntity<>(playerDTOs, HttpStatus.OK);
    }

    @GetMapping("/players-without-sports")
    public ResponseEntity<List<PlayerDTO>> getPlayersWithoutSports() {
        List<Player> playersWithoutSports = playerService.getPlayersWithoutSports();
        List<PlayerDTO> playerDTOList = playersWithoutSports.stream()
                .map(this::mapPlayerToDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(playerDTOList, HttpStatus.OK);
    }

    private PlayerDTO mapPlayerToDTO(Player player) {
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setId(player.getId());
        playerDTO.setPlayerName(player.getName());
        playerDTO.setAge(player.getAge());
        playerDTO.setLevel(player.getLevel());
        playerDTO.setEmail(player.getEmail());
        playerDTO.setGender(player.getGender().toString());

        return playerDTO;
    }

    @PostMapping("/{playerId}/update-sports")
    public ResponseEntity<PlayerDTO> updatePlayerSports(
            @PathVariable Long playerId,
            @RequestBody UpdatePlayerSportsRequestDTO requestDTO) {

        Player updatedPlayer = playerService.updatePlayerSports(playerId, requestDTO);

        if (updatedPlayer != null) {
            PlayerDTO playerDTO = mapPlayerToDTO(updatedPlayer);
            return new ResponseEntity<>(playerDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //CRUD
    @GetMapping("/{playerId}")
    public ResponseEntity<PlayerDTO> getPlayer(@PathVariable Long playerId) {
        Player player = playerService.getPlayer(playerId);
        if (player != null) {
            PlayerDTO playerDTO = mapPlayerToDTO(player);
            return new ResponseEntity<>(playerDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{playerId}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long playerId) {
        playerService.deletePlayer(playerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{playerId}")
    public ResponseEntity<PlayerDTO> updatePlayer(@PathVariable Long playerId, @RequestBody UpdatePlayerRequestDTO requestDTO) {
        Player updatedPlayer = playerService.updatePlayer(playerId, requestDTO);
        if (updatedPlayer != null) {
            PlayerDTO playerDTO = mapPlayerToDTO(updatedPlayer);
            return new ResponseEntity<>(playerDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<PlayerDTO> createPlayer(@RequestBody UpdatePlayerRequestDTO requestDTO) {
        Player createdPlayer = playerService.createPlayer(requestDTO);
        PlayerDTO playerDTO = mapPlayerToDTO(createdPlayer);
        return new ResponseEntity<>(playerDTO, HttpStatus.CREATED);
    }



}
