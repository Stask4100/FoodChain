package com.kornievstas.FoodChain.service;

import com.kornievstas.FoodChain.dto.GoatDto;

import java.util.List;

public interface GoatService {
    GoatDto createGoat(String name);
    List<GoatDto> getAllGoats();
    GoatDto getGoat(String name);
    GoatDto feedGoatWithGrass(String goatName);
}
