package com.example.demo.services;

import com.example.demo.dtos.UpdatePlayerRequestDTO;
import com.example.demo.dtos.UpdatePlayerSportsRequestDTO;
import com.example.demo.entities.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PlayerService {

    public Page<Player> getPlayersBySportsCategory(String category, Pageable pageable);
    public List<Player> getPlayersWithoutSports() ;

    public Player updatePlayerSports(Long playerId, UpdatePlayerSportsRequestDTO requestDTO) ;

    public Player getPlayer(Long playerId) ;

    public void deletePlayer(Long playerId) ;

    public Player updatePlayer(Long playerId, UpdatePlayerRequestDTO requestDTO);

    public Player createPlayer(UpdatePlayerRequestDTO requestDTO) ;

    public Player mapCreatePlayerDTOToEntity(UpdatePlayerRequestDTO requestDTO) ;



}
