package com.kornievstas.FoodChain.dto;

import java.util.ArrayList;
import java.util.List;

public class LionDto {

    private Long id;
    private String name;
    private boolean alive;
    private List<String> eatenGoats = new ArrayList<>();
    private List<String> eatenCows = new ArrayList<>();

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

    public List<String> getEatenGoats() {
        return eatenGoats;
    }

    public void setEatenGoats(List<String> eatenGoats) {
        this.eatenGoats = eatenGoats;
    }

    public List<String> getEatenCows() {
        return eatenCows;
    }

    public void setEatenCows(List<String> eatenCows) {
        this.eatenCows = eatenCows;
    }
}
