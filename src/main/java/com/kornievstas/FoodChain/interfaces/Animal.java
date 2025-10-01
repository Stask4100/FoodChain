package com.kornievstas.FoodChain.interfaces;

public interface Animal<E extends Edible> {
    void eat(E food);
    boolean isAlive();
    void setAlive(boolean alive);
}
