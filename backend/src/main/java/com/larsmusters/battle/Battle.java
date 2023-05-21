package com.larsmusters.battle;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import com.larsmusters.character.Character;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name= "Battle")
@Table(name = "battle")
public class Battle {

    @Id
    @SequenceGenerator(
            name = "battle_sequence",
            sequenceName = "battle_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "battle_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "name",
            columnDefinition = "TEXT"
    )
    private String name;

    @OneToMany(mappedBy = "battle", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Character> characters;

    @Column(name = "create_dt")
    @CreationTimestamp
    private LocalDateTime createDt;

    @Column(name = "update_dt")
    @UpdateTimestamp
    private LocalDateTime updateDt;


    public Battle() {
    }

    public Battle(String name) {
        this.name = name;
    }

    public Battle(Long id, String name, LocalDateTime createDt, LocalDateTime updateDt, List<Character> characters) {
        this.id = id;
        this.name = name;
        this.createDt = createDt;
        this.updateDt = updateDt;
        this.characters = characters;
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

    public List<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
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
        return "Battle{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
