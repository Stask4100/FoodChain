package com.kornievstas.FoodChain.service;

import com.kornievstas.FoodChain.dto.LionDto;

import java.util.List;

public interface LionService {

    LionDto createLion(String name);

    List<LionDto> getAllLions();

    LionDto getLion(String name);

    LionDto feedLionWithGoat(String lionName, String goatName);

    LionDto feedLionWithCow(String lionName, String cowName);

    LionDto feedLion(String lionName, String foodName, String foodType);

}
