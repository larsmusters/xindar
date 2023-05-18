package com.larsmusters.battle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path="api/v1/battle")
public class BattleController {
    private final BattleService battleService;

    @Autowired BattleController(BattleService battleService) { this.battleService = battleService;}

    @GetMapping
    public List<Battle> getBattle() { return battleService.getBattle();}
}
