package com.example.demo.services;

import com.example.demo.dtos.PlayerDTO;
import com.example.demo.dtos.UpdatePlayerRequestDTO;
import com.example.demo.dtos.UpdatePlayerSportsRequestDTO;
import com.example.demo.entities.Player;
import com.example.demo.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    public Page<Player> getPlayersBySportsCategory(String category, Pageable pageable) {
        return playerRepository.findAllBySportsName(category, pageable);
    }
    public List<Player> getPlayersWithoutSports() {
        return playerRepository.findPlayersWithoutSports();
    }

    public Player updatePlayerSports(Long playerId, UpdatePlayerSportsRequestDTO requestDTO) {
        return playerRepository.updatePlayerSports(playerId, requestDTO.getSportIds());
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
