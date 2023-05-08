package com.larsmusters.character;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CharacterService {

    private final CharacterRepository characterRepository;

    @Autowired
    public CharacterService(CharacterRepository characterRepository) { this.characterRepository = characterRepository;}

    public void addNewCharacter(Character character) {
        Optional<Character> characterOptional = characterRepository
                .findCharacterByName(character.getName());
        if (characterOptional.isPresent()) {
            throw new IllegalStateException("email taken");
        }
        characterRepository.save(character);
    }

    public List<Character> getCharacter() {
        return characterRepository.findAll();
    }
}
