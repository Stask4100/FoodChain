package com.kornievstas.FoodChain.mapper;

import com.kornievstas.FoodChain.dto.GrassDto;
import com.kornievstas.FoodChain.entity.Grass;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GrassMapper {
    GrassDto toDto(Grass grass);
    Grass toEntity(GrassDto dto);
}

