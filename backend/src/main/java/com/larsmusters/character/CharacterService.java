package com.larsmusters.character;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterService {

    private final CharacterRepository characterRepository;

    @Autowired
    public CharacterService(CharacterRepository characterRepository) { this.characterRepository = characterRepository;}

    public Long addNewCharacter(Character character) {
//        Optional<Character> characterOptional = characterRepository
//                .findCharacterByName(character.getName());
//        if (characterOptional.isPresent()) {
//            throw new IllegalStateException("Character name taken");
//        }
        characterRepository.save(character);
        return character.getId();
    }

    public List<Character> getCharacter() {
        return characterRepository.findAll();
    }

    public void deleteCharacter(Long characterId) {
        boolean characterExists = characterRepository.existsById(characterId);
        if (!characterExists) {
            throw new IllegalStateException("Character with " + characterId + "does not exist");
        }
        characterRepository.deleteById(characterId);
    }
}
