package com.kornievstas.FoodChain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "grass")
public class Grass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean alive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eaten_by_cow_id")
    private Cow eatenByCow;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eaten_by_goat_id")
    private Goat eatenByGoat;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cow getEatenByCow() {
        return eatenByCow;
    }

    public void setEatenByCow(Cow eatenByCow) {
        this.eatenByCow = eatenByCow;
    }

    public Goat getEatenByGoat() {
        return eatenByGoat;
    }

    public void setEatenByGoat(Goat eatenByGoat) {
        this.eatenByGoat = eatenByGoat;
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
}
