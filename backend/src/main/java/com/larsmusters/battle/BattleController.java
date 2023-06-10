package com.larsmusters.battle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/v1/battle")
public class BattleController {
    private final BattleService battleService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired BattleController(
            BattleService battleService,
            SimpMessagingTemplate simpMessagingTemplate
    ) {
        this.battleService = battleService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @GetMapping
    public List<Battle> getBattles() { return battleService.getBattles();}

    @GetMapping(path="{battleId}")
    public Battle getBattle(@PathVariable("battleId") Long battleId) {return battleService.getBattle(battleId);}

    @PostMapping
    public Battle addBattle(@RequestBody Battle battle) {
        return battleService.addBattle(battle);
    }


    @PutMapping(path="{battleId}")
    public Battle updateBattle(
            @RequestBody Battle battle,
            @PathVariable("battleId"
            )
            Long battleId) {
        Battle battleNew =  battleService.updateBattle(battleId, battle);

        // Send anyone subscribed to this battle an update.
        simpMessagingTemplate.convertAndSend("/topic/battle-state/" + battleNew.getId(), battleNew);

        return battleNew;
    }

    @DeleteMapping(path="{battleId}")
    public void deleteBattle(@PathVariable("battleId") Long battleId) {
        battleService.deleteBattle(battleId);
    }

}
