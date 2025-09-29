package com.kornievstas.FoodChain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "grasses")
public class Grass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eaten_by_goat_id")
    private Goat eatenByGoat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eaten_by_cow_id")
    private Cow eatenByCow;

    public Grass() {
    }

    public Long getId() {
        return id;
    }

    public Goat getEatenByGoat() {
        return eatenByGoat;
    }

    public void setEatenByGoat(Goat eatenByGoat) {
        this.eatenByGoat = eatenByGoat;
    }

    public Cow getEatenByCow() {
        return eatenByCow;
    }

    public void setEatenByCow(Cow eatenByCow) {
        this.eatenByCow = eatenByCow;
    }
}
