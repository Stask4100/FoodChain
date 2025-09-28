package com.kornievstas.FoodChain.repository;

import com.kornievstas.FoodChain.entity.Cow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CowRepository extends JpaRepository<Cow, Long> {
    Optional<Cow> findByName(String name);
    boolean existsByName(String name);
}
