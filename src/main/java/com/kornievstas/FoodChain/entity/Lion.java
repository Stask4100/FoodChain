package com.kornievstas.FoodChain.entity;
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
public class Lion {
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
}