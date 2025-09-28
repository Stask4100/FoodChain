package com.kornievstas.FoodChain.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cows")
public class Cow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean alive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eaten_by_lion_id")
    private Lion eatenByLion;
    @OneToMany(mappedBy = "eatenByCow", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Grass> eatenGrasses = new ArrayList<>();

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

    public Lion getEatenByLion() {
        return eatenByLion;
    }

    public void setEatenByLion(Lion eatenByLion) {
        this.eatenByLion = eatenByLion;
    }

    public List<Grass> getEatenGrasses() {
        return eatenGrasses;
    }

    public void setEatenGrasses(List<Grass> eatenGrasses) {
        this.eatenGrasses = eatenGrasses;
    }

    public void eatGrass(Grass grass) {
        this.eatenGrasses.add(grass);
        grass.setEatenByCow(this);
    }

}
