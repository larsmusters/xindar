package com.larsmusters.character;

import com.fasterxml.jackson.annotation.*;
import com.larsmusters.battle.Battle;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

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

    @ManyToOne
    @JoinColumn(
            name="battle_id",
            foreignKey = @ForeignKey(name="fk_character_battle")
    )
    @JsonBackReference
    private Battle battle;

    @Column(name = "create_dt")
    @CreationTimestamp
    private LocalDateTime createDt;

    @Column(name = "update_dt")
    @UpdateTimestamp
    private LocalDateTime updateDt;

    // must put a default constructor
    public Character() {
    }

    public Character(Long id, String name, int initiative, boolean isUp, Battle battle, LocalDateTime createDt, LocalDateTime updateDt) {
        this.id = id;
        this.name = name;
        this.initiative = initiative;
        this.isUp = isUp;
        this.battle = battle;
        this.createDt = createDt;
        this.updateDt = updateDt;
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

    public LocalDateTime getCreateDt() {
        return createDt;
    }

    public void setCreateDt(LocalDateTime createDt) {
        this.createDt = createDt;
    }

    public LocalDateTime getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(LocalDateTime updateDt) {
        this.updateDt = updateDt;
    }

    @Override
    public String toString() {
        return "Character{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", initiative=" + initiative +
                ", isUp=" + isUp +
                '}';
    }
}
