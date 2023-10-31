package com.example.demo.repositories;

import com.example.demo.entities.Player;
import com.example.demo.entities.Sports;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    Page<Player> findAllBySportsName(String category, Pageable pageable);
//-- SELECT * FROM Player WHERE gender = :gender AND level = :level AND age = :age;
    // Custom query to retrieve players by gender, level, and age
    @Query("SELECT p FROM Player p " +
            "WHERE p.gender = :gender " +
            "AND p.level = :level " +
            "AND p.age = :age")
    List<Player> findByGenderLevelAndAge(
            @Param("gender") String gender,
            @Param("level") int level,
            @Param("age") int age);

    @Query("SELECT p FROM Player p WHERE p.sports IS EMPTY")
    List<Player> findPlayersWithoutSports();

    @Modifying
    @Transactional
    @Query("UPDATE Player p SET p.sports = :sports WHERE p.id = :playerId")
    void updatePlayerSports(@Param("playerId") Long playerId, @Param("sports") Set<Sports> sports);

}
