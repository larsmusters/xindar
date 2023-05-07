package com.larsmusters.character;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterService {

    private final CharacterRepository characterRepository;

    @Autowired
    public CharacterService(CharacterRepository characterRepository) { this.characterRepository = characterRepository;}

    public List<Character> getCharacter() {
        return characterRepository.findAll();
    }
}
