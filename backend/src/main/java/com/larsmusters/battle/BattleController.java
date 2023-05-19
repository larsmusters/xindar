package com.larsmusters.battle;

import com.larsmusters.character.Character;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/v1/battle")
public class BattleController {
    private final BattleService battleService;

    @Autowired BattleController(BattleService battleService) { this.battleService = battleService;}

    @GetMapping
    public List<Battle> getBattle() { return battleService.getBattle();}


    @PostMapping
    public Long addBattle(@RequestBody Battle battle) {
        return battleService.addBattle(battle);
    }

    @DeleteMapping(path="{battleId}")
    public void deleteBattle(@PathVariable("battleId") Long battleId) {
        battleService.deleteBattle(battleId);
    }

}
