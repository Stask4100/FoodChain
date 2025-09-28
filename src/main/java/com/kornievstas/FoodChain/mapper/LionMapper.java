package com.kornievstas.FoodChain.mapper;

import com.kornievstas.FoodChain.dto.LionDto;
import com.kornievstas.FoodChain.entity.Cow;
import com.kornievstas.FoodChain.entity.Goat;
import com.kornievstas.FoodChain.entity.Lion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface LionMapper {

    @Mapping(target = "eatenGoats", expression = "java(mapGoatsToNames(lion.getEatenGoats()))")
    @Mapping(target = "eatenCows", expression = "java(mapCowsToNames(lion.getEatenCows()))")
    LionDto toDto(Lion lion);

    @Mapping(target = "eatenGoats", ignore = true)
    @Mapping(target = "eatenCows", ignore = true)
    Lion toEntity(LionDto dto);

    default List<String> mapGoatsToNames(List<Goat> goats) {
        if (goats == null) return null;
        return goats.stream().map(Goat::getName).collect(Collectors.toList());
    }

    default List<String> mapCowsToNames(List<Cow> cows) {
        if (cows == null) return null;
        return cows.stream().map(Cow::getName).collect(Collectors.toList());
    }
}


