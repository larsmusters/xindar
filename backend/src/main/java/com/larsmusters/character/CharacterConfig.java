package com.larsmusters.character;

import com.larsmusters.battle.Battle;
import com.larsmusters.battle.BattleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Profile("!prod")
@Configuration
public class CharacterConfig {

    @Bean
    CommandLineRunner commandLineRunner(
            CharacterRepository repository, BattleRepository battleRepository) {
        return args -> {
            Battle battle1 = new Battle(
                    "Battle 1"
            );
            Battle battle2 = new Battle(
                    "Battle 2"
            );
            battleRepository.saveAll(List.of(battle1, battle2));
            Character hero1 = new Character(
                    "Hero 1",
                    20,
                    battle1.getId()
            );

            Character monster1 = new Character(
                    "Monster 1 ",
                    10,
                    battle1.getId()
            );

            Character hero2 = new Character(
                    "Hero 2",
                    30,
                    battle2.getId()
            );
            Character hero3 = new Character(
                    "Hero 3",
                    3,
                    battle2.getId()
            );
            Character monster2 = new Character(
                    "Monster 2",
                    60,
                    battle2.getId()
            );
            repository.saveAll(List.of(hero1,monster1,hero2,monster2,hero3));
        };
    }
}
