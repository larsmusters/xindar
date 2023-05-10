package com.larsmusters.character;

import jakarta.persistence.*;

@Entity
@Table
public class Character {
    @Id
    @SequenceGenerator(
            name="character_sequence",
            sequenceName = "character_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "character_sequence"
    )
    private Long id;
    private String name;
    private int initiative;
    private boolean isUp;
    private Long battleId;


    public Character(Long id) {
        this.id = id;
    }

    // must put a default constructor
    public Character() {
    }

    public Character(Long id, String name, int initiative, boolean isUp, Long battleId) {
        this.id = id;
        this.name = name;
        this.initiative = initiative;
        this.isUp = isUp;
        this.battleId = battleId;
    }

    public Character(String name, int initiative, boolean isUp, Long battleId) {
        this.name = name;
        this.initiative = initiative;
        this.isUp = isUp;
        this.battleId = battleId;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getInitiative() {
        return initiative;
    }

    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }

    public boolean isUp() {
        return isUp;
    }

    public void setUp(boolean up) {
        isUp = up;
    }

    public Long getBattleId() {
        return battleId;
    }

    public void setBattleId(Long battleId) {
        this.battleId = battleId;
    }
}
