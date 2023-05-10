package com.larsmusters.character;

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
            CharacterRepository repository) {
        return args -> {
            Character zombie = new Character(
                    "Zombie",
                    20,
                    true,
                    1L

            );

            Character vampire = new Character(
                    "Vampire",
                    10,
                    false,
                    1L
            );

            repository.saveAll(List.of(zombie,vampire));
        };
    }
}
