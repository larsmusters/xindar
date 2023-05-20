package com.larsmusters.character;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/v1/character")
public class CharacterController {
    private final CharacterService characterService;

    @Autowired
    public CharacterController(CharacterService characterService) { this.characterService = characterService; }

    @GetMapping
    public List<Character> getCharacter() { return characterService.getCharacter();}

    @PostMapping
    public Character registerNewCharacter(@RequestBody Character character) {
        return characterService.addNewCharacter(character);
    }

    @DeleteMapping(path="{characterId}")
    public void deleteCharacter(@PathVariable("characterId") Long characterId) {
        characterService.deleteCharacter(characterId);
    }
}
