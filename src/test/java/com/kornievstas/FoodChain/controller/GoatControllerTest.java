package com.kornievstas.FoodChain.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kornievstas.FoodChain.dto.GoatDto;
import com.kornievstas.FoodChain.exception.NotFoundException;
import com.kornievstas.FoodChain.exception.GlobalExceptionHandler;
import com.kornievstas.FoodChain.service.GoatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = GoatController.class)
@Import(GlobalExceptionHandler.class)
class GoatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GoatService goatService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        reset(goatService);
    }

    @Test
    void createGoat_shouldReturnGoatDto() throws Exception {
        GoatDto dto = new GoatDto();
        dto.setId(1L);
        dto.setName("Koza1");
        dto.setAlive(true);
        dto.setEatenGrassNames(List.of());

        when(goatService.createGoat("Koza1")).thenReturn(dto);

        mockMvc.perform(post("/goats/Koza1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Koza1"))
                .andExpect(jsonPath("$.alive").value(true))
                .andExpect(jsonPath("$.eatenGrassNames").isArray());
    }

    @Test
    void getAllGoats_shouldReturnList() throws Exception {
        GoatDto dto = new GoatDto();
        dto.setId(1L);
        dto.setName("Koza1");
        dto.setAlive(true);
        dto.setEatenGrassNames(List.of());

        when(goatService.getAllGoats()).thenReturn(List.of(dto));

        mockMvc.perform(get("/goats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Koza1"))
                .andExpect(jsonPath("$[0].alive").value(true));
    }

    @Test
    void getGoat_shouldReturnGoatDto() throws Exception {
        GoatDto dto = new GoatDto();
        dto.setId(1L);
        dto.setName("Koza1");
        dto.setAlive(true);
        dto.setEatenGrassNames(List.of());

        when(goatService.getGoat("Koza1")).thenReturn(dto);

        mockMvc.perform(get("/goats/Koza1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Koza1"))
                .andExpect(jsonPath("$.alive").value(true));
    }

    @Test
    void feedGoatWithGrass_success() throws Exception {
        GoatDto updated = new GoatDto();
        updated.setId(1L);
        updated.setName("Koza1");
        updated.setAlive(true);
        updated.setEatenGrassNames(List.of("Grass1"));

        when(goatService.feedGoatWithGrass("Koza1", "Grass1")).thenReturn(updated);

        mockMvc.perform(put("/goats/Koza1/eat-grass/Grass1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eatenGrassNames[0]").value("Grass1"));
    }

    @Test
    void feedGoatWithGrass_notFound_shouldReturn404() throws Exception {
        when(goatService.feedGoatWithGrass("Koza1", "Grass1"))
                .thenThrow(new NotFoundException("Grass not found"));

        mockMvc.perform(put("/goats/Koza1/eat-grass/Grass1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Grass not found"));
    }
}
