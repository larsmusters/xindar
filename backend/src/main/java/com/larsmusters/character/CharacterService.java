package com.larsmusters.character;

import com.larsmusters.battle.Battle;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterService {

    private final CharacterRepository characterRepository;

    @Autowired
    public CharacterService(CharacterRepository characterRepository) { this.characterRepository = characterRepository;}

    public Character addNewCharacter(Character character) {
        characterRepository.save(character);
        return character;
    }

    public List<Character> getCharacters() {
        return characterRepository.findAll();
    }

    public Character getCharacter(Long characterId) {
        return characterRepository.findById(characterId)
                .orElseThrow(() -> new IllegalStateException("Character with " + characterId + " does not exist."));
    }

    public void deleteCharacter(Long characterId) {
        boolean characterExists = characterRepository.existsById(characterId);
        if (!characterExists) {
            throw new IllegalStateException("Character with " + characterId + "does not exist");
        }
        characterRepository.deleteById(characterId);
    }

    @Transactional
    public Character updateCharacter(Long characterId, Character character) {
        Character characterInDB = characterRepository.findById(characterId)
                .orElseThrow(() -> new IllegalStateException("Character with " + characterId + " does not exist."));

        characterInDB.setName(character.getName());
        characterInDB.setBattleId(character.getBattleId());
        characterInDB.setUp(character.isUp());
        characterInDB.setInitiative(character.getInitiative());

        return characterInDB;
    }
}
