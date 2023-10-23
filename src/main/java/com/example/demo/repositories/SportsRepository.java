package com.example.demo.repositories;

import com.example.demo.entities.Sports;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SportsRepository extends JpaRepository<Sports, Long> {

    // Custom query to find sports by name
    @Query("SELECT s FROM Sports s WHERE s.name = :name")
    Sports findByName(@Param("name") String name);

    @Query("SELECT s FROM Sports s WHERE s.id = :id")
    Optional<Sports> findById(@Param("id") Long id);


    Sports save(Sports sports);

    // Update an existing sports entry
    Sports saveAndFlush(Sports sports);

    // Delete a sports entry by ID
    void deleteById(Long id);

    List<Sports> findAll();

    // Custom query to retrieve sports associated with multiple players
    @Query("SELECT s FROM Sports s " +
            "JOIN s.players p " +
            "GROUP BY s " +
            "HAVING COUNT(p) >= 2")
    List<Sports> findSportsWithMultiplePlayers();

    @Query("SELECT s FROM Sports s " +
            "WHERE s.players IS EMPTY")
    List<Sports> findSportsWithNoPlayers();


}

