package com.kornievstas.FoodChain.service;

import com.kornievstas.FoodChain.dto.CowDto;

import java.util.List;

public interface CowService {
    CowDto createCow(String name);
    List<CowDto> getAllCows();
    CowDto getCow(String name);
    CowDto feedCowWithGrass(String cowName);
}
