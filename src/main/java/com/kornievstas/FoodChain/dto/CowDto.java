package com.kornievstas.FoodChain.dto;

import java.util.List;

public class CowDto {

    private Long id;
    private String name;
    private boolean alive;
    private List<String> eatenGrassNames;

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

    public List<String> getEatenGrassNames() {
        return eatenGrassNames;
    }

    public void setEatenGrassNames(List<String> eatenGrassNames) {
        this.eatenGrassNames = eatenGrassNames;
    }
}
