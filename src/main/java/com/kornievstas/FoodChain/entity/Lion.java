package com.kornievstas.FoodChain.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lions")
public class Lion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean alive;

    @OneToMany(mappedBy = "eatenByLion", fetch = FetchType.LAZY)
    private List<Cow> eatenCows = new ArrayList<>();
    @OneToMany(mappedBy = "eatenByLion", fetch = FetchType.LAZY)
    private List<Goat> eatenGoats = new ArrayList<>();

    public Lion() {
        this.alive = true;
    }

    public Lion(String name) {
        this.name = name;
        this.alive = true;
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

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public List<Cow> getEatenCows() {
        return eatenCows;
    }

    public void setEatenCows(List<Cow> eatenCows) {
        this.eatenCows = eatenCows;
    }

    public List<Goat> getEatenGoats() {
        return eatenGoats;
    }

    public void setEatenGoats(List<Goat> eatenGoats) {
        this.eatenGoats = eatenGoats;
    }
}
