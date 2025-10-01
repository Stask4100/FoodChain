package com.kornievstas.FoodChain.entity;
import com.kornievstas.FoodChain.interfaces.Animal;
import com.kornievstas.FoodChain.interfaces.Edible;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "cows")
@Getter
@Setter
@NoArgsConstructor
public class Cow implements Animal<Grass>, Edible {

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

    public void eatGrass(Grass grass) {
        this.eatenGrasses.add(grass);
        grass.setEatenByCow(this);
    }

    @Override
    public void eat(Grass food) {
        eatenGrasses.add(food);
        food.setEatenByCow(this);
    }

    @Override
    public boolean isAlive() {
        return alive;
    }

    @Override
    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}