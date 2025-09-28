// Mapper виступає шаром-перетворювачем між базовою логікою (Entity) і API-відповіддю (DTO).
package com.kornievstas.FoodChain.mapper;

import com.kornievstas.FoodChain.dto.LionDto;
import com.kornievstas.FoodChain.entity.Cow;
import com.kornievstas.FoodChain.entity.Goat;
import com.kornievstas.FoodChain.entity.Lion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

// MapStruct автоматично створює імплементацію маппера і реєструє її як Spring-бін
@Mapper(componentModel = "spring")
public interface LionMapper {

    // Перетворює Lion → LionDto
    // Ми явно кажемо MapStruct як саме брати імена з об'єктів Goat та Cow
    @Mapping(target = "eatenGoats", expression = "java(mapGoatsToNames(lion.getEatenGoats()))")
    @Mapping(target = "eatenCows", expression = "java(mapCowsToNames(lion.getEatenCows()))")
    LionDto toDto(Lion lion);

    // Зворотнє перетворення LionDto → Lion
    // Тут ігноруємо поля eatenGoats і eatenCows, бо вони не зберігаються прямо в Lion
    @Mapping(target = "eatenGoats", ignore = true)
    @Mapping(target = "eatenCows", ignore = true)
    Lion toEntity(LionDto dto);

    // Допоміжний метод: бере список козлів і повертає список їхніх імен
    default List<String> mapGoatsToNames(List<Goat> goats) {
        if (goats == null) return null;
        return goats.stream().map(Goat::getName).collect(Collectors.toList());
    }

    // Те саме для корів
    default List<String> mapCowsToNames(List<Cow> cows) {
        if (cows == null) return null;
        return cows.stream().map(Cow::getName).collect(Collectors.toList());
    }
}



