package com.larsmusters.character;

import com.larsmusters.battle.Battle;
import com.larsmusters.battle.BattleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/v1/character")
public class CharacterController {
    private final CharacterService characterService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final BattleService battleService;


    @Autowired
    public CharacterController(
            CharacterService characterService,
            SimpMessagingTemplate simpMessagingTemplate,
            BattleService battleService
    ) {
        this.characterService = characterService;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.battleService = battleService;
    }

    @GetMapping
    public List<Character> getCharacter() { return characterService.getCharacters();}

    @PostMapping
    public Character registerNewCharacter(@RequestBody Character character) {
        Character characterNew = characterService.addNewCharacter(character);

        // Send any battles going on with this character an update.
        Battle battle = battleService.getBattle(characterNew.getBattleId());
        simpMessagingTemplate.convertAndSend("/topic/battle-state/" + battle.getId(), battle);
        return characterNew;
    }


    @PutMapping(path="{characterId}")
    public Character updateCharacter(
            @RequestBody Character character,
            @PathVariable("characterId"
            )
            Long characterId) {
        Character characterNew =  characterService.updateCharacter(characterId, character);

        // Send any battles going on with this character an update.
        Battle battle = battleService.getBattle(characterNew.getBattleId());
        simpMessagingTemplate.convertAndSend("/topic/battle-state/" + battle.getId(), battle);
        return characterNew;
    }


    @DeleteMapping(path="{characterId}")
    public void deleteCharacter(@PathVariable("characterId") Long characterId) {
        Character character = characterService.getCharacter(characterId);
        Battle battle = battleService.getBattle(character.getBattleId());

        characterService.deleteCharacter(characterId);

        // Send any battles going on with this character an update.
        simpMessagingTemplate.convertAndSend("/topic/battle-state/" + battle.getId(), battle);
    }
}
