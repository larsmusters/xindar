package com.larsmusters.battle;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import com.larsmusters.character.Character;

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

    @OneToMany(mappedBy = "battle", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JsonIgnoreProperties(value = "battle")
    @JsonManagedReference
//    @JsonIgnore
//    @JsonIdentityInfo(
//            generator = ObjectIdGenerators.PropertyGenerator.class,
//            property = "id"
//    )
    private List<Character> characters;


    public Battle() {

    }

    public Battle(Long id) {
        this.id = id;
    }

    public Battle(String name, List<Character> characters) {
        this.name = name;
        this.characters = characters;
    }

    public Battle(Long id, String name, List<Character> characters) {
        this.id = id;
        this.name = name;
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
}
