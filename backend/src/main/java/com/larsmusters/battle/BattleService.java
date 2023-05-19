package com.larsmusters.battle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BattleService {

    private final BattleRepository battleRepository;

    @Autowired BattleService(BattleRepository battleRepository) { this.battleRepository = battleRepository;}

    public List<Battle> getBattle() { return battleRepository.findAll();}

    public Long addBattle(Battle battle) {
        battleRepository.save(battle);
        return battle.getId();
    }

    public void deleteBattle(Long battleId) {
        boolean battleExists = battleRepository.existsById(battleId);
        if (!battleExists) {
            throw new IllegalStateException("Battle with " + battleId + " does not exist.");
        }
        battleRepository.deleteById(battleId);
    }
}
