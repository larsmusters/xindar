package com.larsmusters.character;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.larsmusters.battle.Battle;
import jakarta.persistence.*;

@Entity(name = "Character")
@Table(name = "character")
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
    @Column(
            name="id",
            updatable = false
    )
    private Long id;

    @Column(
            name="name",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String name;

    @Column(
            name="initiative"
    )
    private int initiative;

    @Column(
            name="is_up"
    )
    private boolean isUp;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name="battle_id",
            foreignKey = @ForeignKey(name="fk_character_battle")
    )
//    @JsonIgnoreProperties(value = "character")
    @JsonBackReference
//    @JsonIdentityInfo(
//            generator = ObjectIdGenerators.PropertyGenerator.class,
//            property = "id"
//    )
    private Battle battle;


    public Character(Long id) {
        this.id = id;
    }

    // must put a default constructor
    public Character() {
    }

    public Character(Long id, String name, int initiative, boolean isUp, Battle battle) {
        this.id = id;
        this.name = name;
        this.initiative = initiative;
        this.isUp = isUp;
        this.battle = battle;
    }

    public Character(String name, int initiative, boolean isUp, Battle battle) {
        this.name = name;
        this.initiative = initiative;
        this.isUp = isUp;
        this.battle = battle;
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

    public Battle getBattle() {
        return battle;
    }

    public void setBattle(Battle battle) {
        this.battle = battle;
    }
}
