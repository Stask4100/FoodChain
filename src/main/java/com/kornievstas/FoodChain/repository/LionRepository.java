package com.kornievstas.FoodChain.repository;


import com.kornievstas.FoodChain.entity.Lion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LionRepository extends JpaRepository<Lion, Long> {
    Optional<Lion> findByName(String name);
    boolean existsByName(String name);
}
