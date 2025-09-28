package com.kornievstas.FoodChain.repository;

import com.kornievstas.FoodChain.entity.Goat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GoatRepository extends JpaRepository<Goat, Long> {
    Optional<Goat> findByName(String name);
    boolean existsByName(String name);
}
