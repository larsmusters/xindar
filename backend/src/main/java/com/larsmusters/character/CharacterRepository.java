package com.larsmusters.character;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Long> {

    @Query("SELECT c from Character c WHERE c.name = ?1")
    Optional<Character> findCharacterByName(String name);
}
