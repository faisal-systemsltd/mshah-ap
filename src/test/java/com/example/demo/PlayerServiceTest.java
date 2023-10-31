package com.example.demo;

import com.example.demo.dtos.UpdatePlayerRequestDTO;
import com.example.demo.dtos.UpdatePlayerSportsRequestDTO;
import com.example.demo.entities.Player;
import com.example.demo.repositories.PlayerRepository;
import com.example.demo.services.impl.PlayerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerServiceImpl playerService;

    @BeforeEach
    public void setUp() {
        Player player = new Player();
        player.setId(1L);
        player.setName("John");
        player.setAge(25);
        player.setLevel(5);
        player.setEmail("example@example.com");

        Mockito.lenient().when(playerRepository.findById(1L)).thenReturn(Optional.of(player));
        Mockito.lenient().when(playerRepository.save(player)).thenReturn(player);
    }

    @Test
    public void testUpdatePlayer() {
        UpdatePlayerRequestDTO updateRequest = new UpdatePlayerRequestDTO();
        updateRequest.setName("Updated John");
        updateRequest.setAge(26);
        updateRequest.setLevel(6);
        updateRequest.setEmail("updated@example.com");

        Player updatedPlayer = playerService.updatePlayer(1L, updateRequest);

        assertNotNull(updatedPlayer);
        assertEquals("Updated John", updatedPlayer.getName());
        assertEquals(26, updatedPlayer.getAge());
        assertEquals(6, updatedPlayer.getLevel());
        assertEquals("updated@example.com", updatedPlayer.getEmail());
    }

    @Test
    public void testGetPlayer() {
        Long playerId = 1L;
        Player playerEntity = new Player();
        playerEntity.setEmail("example@example.com");

        Mockito.lenient().when(playerRepository.findById(playerId)).thenReturn(Optional.of(playerEntity));

        Player player = playerService.getPlayer(playerId);

        assertNotNull(player);
        assertEquals("example@example.com", player.getEmail());
    }

    public Player createPlayer(UpdatePlayerRequestDTO requestDTO) {
        Player player = new Player();
        player.setName(requestDTO.getName());
        player.setAge(requestDTO.getAge());
        player.setLevel(requestDTO.getLevel());
        player.setEmail(requestDTO.getEmail());

        // Save the player in the repository
        return playerRepository.save(player);
    }

    @Test
    public void testUpdatePlayerSports() {
        Long playerId = 1L;
        UpdatePlayerSportsRequestDTO sportsRequest = new UpdatePlayerSportsRequestDTO();
        Set<Long> sportIds = new HashSet<>();
        sportIds.add(1L);
        sportsRequest.setSportIds(sportIds);

        Player updatedPlayer = playerService.updatePlayerSports(playerId, sportsRequest);

        assertNotNull(updatedPlayer);
        assertEquals(1, updatedPlayer.getSports().size());
    }

    @Test
    public void testGetPlayersBySportsCategory() {
        String category = "category";
        Pageable pageable = PageRequest.of(0, 10);
        Page<Player> playersPage = new PageImpl<>(Collections.singletonList(new Player()));

        Mockito.lenient().when(playerRepository.findAllBySportsName(category, pageable)).thenReturn(playersPage);

        Page<Player> result = playerService.getPlayersBySportsCategory(category, pageable);

        assertNotNull(result);
    }
}
