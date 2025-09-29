package com.kornievstas.FoodChain.entity;
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
    public void eatGrass(Grass grass) {
        this.eatenGrasses.add(grass);
        grass.setEatenByCow(this);
    }
}