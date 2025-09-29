package com.kornievstas.FoodChain.repository;

import com.kornievstas.FoodChain.entity.Grass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GrassRepository extends JpaRepository<Grass, Long> {
}
