package com.kornievstas.FoodChain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Data // генерує гетери, сетери, equals, hashCode, toString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LionDto {

    private Long id;

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2, max = 20, message = "Name must be between 2 and 20 characters")
    private String name;

    @NotNull(message = "Alive status must be specified")
    private Boolean alive;

    @Builder.Default
    private List<String> eatenGoats = new ArrayList<>();

    @Builder.Default
    private List<String> eatenCows = new ArrayList<>();
}
