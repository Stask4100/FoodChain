package com.kornievstas.FoodChain.mapper;

import com.kornievstas.FoodChain.dto.LionDto;
import com.kornievstas.FoodChain.entity.Cow;
import com.kornievstas.FoodChain.entity.Goat;
import com.kornievstas.FoodChain.entity.Lion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface LionMapper {

    @Mapping(target = "eatenGoats", source = "eatenGoats", qualifiedByName = "mapGoatsToNames")
    @Mapping(target = "eatenCows", source = "eatenCows", qualifiedByName = "mapCowsToNames")
    LionDto toDto(Lion lion);

    @Mapping(target = "eatenGoats", ignore = true)
    @Mapping(target = "eatenCows", ignore = true)
    Lion toEntity(LionDto dto);

    @Named("mapGoatsToNames")
    static List<String> mapGoatsToNames(List<Goat> goats) {
        if (goats == null) return null;
        return goats.stream().map(Goat::getName).collect(Collectors.toList());
    }

    @Named("mapCowsToNames")
    static List<String> mapCowsToNames(List<Cow> cows) {
        if (cows == null) return null;
        return cows.stream().map(Cow::getName).collect(Collectors.toList());
    }
}
