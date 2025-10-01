package com.kornievstas.FoodChain.entity;

import com.kornievstas.FoodChain.interfaces.Edible;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "grasses")
@Getter
@Setter
@NoArgsConstructor
public class Grass implements Edible {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eaten_by_goat_id")
    private Goat eatenByGoat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eaten_by_cow_id")
    private Cow eatenByCow;
}
