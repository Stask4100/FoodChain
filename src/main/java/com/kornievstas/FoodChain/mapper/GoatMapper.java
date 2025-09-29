package com.kornievstas.FoodChain.mapper;

import com.kornievstas.FoodChain.dto.GoatDto;
import com.kornievstas.FoodChain.entity.Goat;
import com.kornievstas.FoodChain.entity.Grass;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface GoatMapper {

    @Mapping(target = "eatenGrassNames", expression = "java(mapGrassesToNames(goat.getEatenGrasses()))")
    GoatDto toDto(Goat goat);

    @Mapping(target = "eatenGrasses", ignore = true)
    Goat toEntity(GoatDto dto);

    default List<String> mapGrassesToNames(List<Grass> grasses) {
        if (grasses == null) return null;
        return grasses.stream()
                .map(g -> "Grass")
                .collect(Collectors.toList());
    }
}
