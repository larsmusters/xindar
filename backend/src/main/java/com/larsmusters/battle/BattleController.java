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
            Long battleId) {return battleService.updateBattle(battleId, battle);}

    @DeleteMapping(path="{battleId}")
    public void deleteBattle(@PathVariable("battleId") Long battleId) {
        battleService.deleteBattle(battleId);
    }

}
