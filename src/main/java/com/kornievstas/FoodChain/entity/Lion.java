package com.kornievstas.FoodChain.entity;
import com.kornievstas.FoodChain.exception.InvalidActionException;
import com.kornievstas.FoodChain.interfaces.Animal;
import com.kornievstas.FoodChain.interfaces.Edible;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lions")
@Getter
@Setter
@NoArgsConstructor
public class Lion implements Animal<Edible> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean alive = true;

    @OneToMany(mappedBy = "eatenByLion", fetch = FetchType.LAZY)
    private List<Cow> eatenCows = new ArrayList<>();

    @OneToMany(mappedBy = "eatenByLion", fetch = FetchType.LAZY)
    private List<Goat> eatenGoats = new ArrayList<>();

    public Lion(String name) {
        this.name = name;
        this.alive = true;
    }

    @Override
    public void eat(Edible food) {
        if (food instanceof Cow cow) {
            cow.setAlive(false);
            eatenCows.add(cow);
        } else if (food instanceof Goat goat) {
            goat.setAlive(false);
            eatenGoats.add(goat);
        } else {
            throw new InvalidActionException("Lion can only eat goats or cows");
        }
    }

    @Override
    public boolean isAlive() {
        return alive;
    }

    @Override
    public void setAlive(boolean alive) {
        this.alive=alive;
    }
}