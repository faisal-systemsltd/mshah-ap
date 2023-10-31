package com.example.demo.services.impl;

import com.example.demo.dtos.UpdatePlayerRequestDTO;
import com.example.demo.dtos.UpdatePlayerSportsRequestDTO;
import com.example.demo.entities.Player;
import com.example.demo.entities.Sports;
import com.example.demo.repositories.PlayerRepository;
import com.example.demo.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService {


    @Autowired
    private PlayerRepository playerRepository;

    public Page<Player> getPlayersBySportsCategory(String category, Pageable pageable) {
        return playerRepository.findAllBySportsName(category, pageable);
    }
    public List<Player> getPlayersWithoutSports() {
        return playerRepository.findPlayersWithoutSports();
    }

    @Transactional
    public Player updatePlayerSports(Long playerId, UpdatePlayerSportsRequestDTO requestDTO) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new EntityNotFoundException("Player not found with ID: " + playerId));

        Set<Sports> sports = requestDTO.getSportIds().stream()
                .map(sportId -> {
                    Sports sport = new Sports();
                    sport.setId(sportId);
                    return sport;
                })
                .collect(Collectors.toSet());

        player.setSports(sports);
        return playerRepository.save(player);
    }

    public Player getPlayer(Long playerId) {
        return playerRepository.findById(playerId).orElse(null);
    }

    public void deletePlayer(Long playerId) {
        playerRepository.deleteById(playerId);
    }

    public Player updatePlayer(Long playerId, UpdatePlayerRequestDTO requestDTO) {
        Player existingPlayer = playerRepository.findById(playerId).orElse(null);
        if (existingPlayer != null) {
            // Update the player's attributes from the requestDTO
            // You can add validation and error handling as needed
            existingPlayer.setName(requestDTO.getName());
            existingPlayer.setAge(requestDTO.getAge());
            existingPlayer.setLevel(requestDTO.getLevel());
            existingPlayer.setEmail(requestDTO.getEmail());
            existingPlayer.setGender(requestDTO.getGender());
            // Update other attributes as required
            return playerRepository.save(existingPlayer);
        }
        return null;
    }

    public Player createPlayer(UpdatePlayerRequestDTO requestDTO) {
        Player player = mapCreatePlayerDTOToEntity(requestDTO);
        return playerRepository.save(player);
    }

    public Player mapCreatePlayerDTOToEntity(UpdatePlayerRequestDTO requestDTO) {
        Player player = new Player();
        player.setName(requestDTO.getName());
        player.setAge(requestDTO.getAge());
        player.setLevel(requestDTO.getLevel());
        player.setEmail(requestDTO.getEmail());
        player.setGender(requestDTO.getGender());

        // You can set other attributes as needed

        return player;
    }

}
