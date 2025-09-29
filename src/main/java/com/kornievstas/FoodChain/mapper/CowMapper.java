package com.kornievstas.FoodChain.mapper;

import com.kornievstas.FoodChain.dto.CowDto;
import com.kornievstas.FoodChain.entity.Cow;
import com.kornievstas.FoodChain.entity.Grass;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CowMapper {

    @Mapping(target = "eatenGrassNames", expression = "java(mapGrassesToNames(cow.getEatenGrasses()))")
    CowDto toDto(Cow cow);

    @Mapping(target = "eatenGrasses", ignore = true)
    Cow toEntity(CowDto dto);

    default List<String> mapGrassesToNames(List<Grass> grasses) {
        if (grasses == null) return null;
        return grasses.stream()
                .map(g -> "Grass")
                .collect(Collectors.toList());
    }
}
