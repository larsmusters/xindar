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
                    123,
                    3

            );

            Character vampire = new Character(
                    "Vampire",
                    567,
                    2
            );

            repository.saveAll(List.of(zombie,vampire));
        };
    }
}
