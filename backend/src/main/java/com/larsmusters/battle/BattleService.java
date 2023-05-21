package com.larsmusters.battle;

import com.larsmusters.character.Character;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BattleService {

    private final BattleRepository battleRepository;

    @Autowired BattleService(BattleRepository battleRepository) { this.battleRepository = battleRepository;}

    public List<Battle> getBattles() { return battleRepository.findAll();}

    public Battle getBattle(Long battleId) {
        return battleRepository.findById(battleId)
                .orElseThrow(() -> new IllegalStateException("Battle with " + battleId + "does not exist."));
    }

    public Battle addBattle(Battle battle) {
        battleRepository.save(battle);
        return battle;
    }

    public void deleteBattle(Long battleId) {
        boolean battleExists = battleRepository.existsById(battleId);
        if (!battleExists) {
            throw new IllegalStateException("Battle with " + battleId + " does not exist.");
        }
        battleRepository.deleteById(battleId);
    }

    @Transactional
    public Battle updateBattle(Long battleId, Battle battle) {
        Battle battleInDB = battleRepository.findById(battleId)
                .orElseThrow(() -> new IllegalStateException("Battle with " + battleId + "does not exist."));

        battleInDB.setName(battle.getName());

        // To update the relations, first clear them, then add them in one by one.
        battleInDB.getCharacters().clear();
        for (Character character : battle.getCharacters()) {
            character.setBattle(battle);
            battleInDB.getCharacters().add(character);
        }

        return battleRepository.save(battleInDB);
    }
}
