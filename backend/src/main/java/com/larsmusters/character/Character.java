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
    private int health;
    private int size;

    @Transient
    private String sizeName;

    // must put a default constructor
    public Character() {

    }

    public Character(Long id) {
    }

    public Character(Long id,
                     String name,
                     int health,
                     int size) {
        this.id = id;
        this.name = name;
        this.health = health;
        this.size = size;
    }

    public Character(String name,
                     int health,
                     int size) {
        this.name = name;
        this.health = health;
        this.size = size;
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

    public int getHealth() { return health; }

    public void setHealth(int health) { this.health = health; }

    public int getSize() { return size; }

    public void setSize(int size) { this.size = size; }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", health='" + health + '\'' +
                ", size=" + size +
                '}';
    }
}
