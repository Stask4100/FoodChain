package com.kornievstas.FoodChain.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kornievstas.FoodChain.dto.LionDto;
import com.kornievstas.FoodChain.exception.AlreadyExistsException;
import com.kornievstas.FoodChain.exception.NotFoundException;
import com.kornievstas.FoodChain.exception.GlobalExceptionHandler;
import com.kornievstas.FoodChain.service.LionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = LionController.class)
@Import(GlobalExceptionHandler.class)
class LionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LionService lionService;

    @Autowired
    private ObjectMapper objectMapper;

    private LionDto simba;

    @BeforeEach
    void setUp() {
        simba = LionDto.builder()
                .id(1L)
                .name("Simba")
                .alive(true)
                .build();

        reset(lionService);
    }

    @Test
    void createLion_success() throws Exception {
        when(lionService.createLion("Simba")).thenReturn(simba);

        mockMvc.perform(post("/lions/Simba"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Simba"))
                .andExpect(jsonPath("$.alive").value(true));
    }

    @Test
    void createLion_alreadyExists_shouldReturn409() throws Exception {
        when(lionService.createLion("Simba"))
                .thenThrow(new AlreadyExistsException("Lion with name Simba already exists"));

        mockMvc.perform(post("/lions/Simba"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Lion with name Simba already exists"));
    }

    @Test
    void getAllLions_success() throws Exception {
        when(lionService.getAllLions()).thenReturn(List.of(simba));

        mockMvc.perform(get("/lions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Simba"));
    }

    @Test
    void getLion_success() throws Exception {
        when(lionService.getLion("Simba")).thenReturn(simba);

        mockMvc.perform(get("/lions/Simba"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Simba"));
    }

    @Test
    void getLion_notFound_shouldReturn404() throws Exception {
        when(lionService.getLion("Mufasa"))
                .thenThrow(new NotFoundException("Lion not found: Mufasa"));

        mockMvc.perform(get("/lions/Mufasa"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Lion not found: Mufasa"));
    }

    @Test
    void feedLionWithGoat_success() throws Exception {
        LionDto updated = simba.toBuilder()
                .eatenGoats(List.of("Koza2"))
                .build();

        when(lionService.feedLion("Simba", "Koza2", "goat")).thenReturn(updated);

        mockMvc.perform(put("/lions/Simba")
                        .param("foodName", "Koza2")
                        .param("foodType", "goat"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eatenGoats[0]").value("Koza2"));
    }

    @Test
    void feedLionWithGoat_notFound_shouldReturn404() throws Exception {
        when(lionService.feedLion("Simba", "KozaX", "goat"))
                .thenThrow(new NotFoundException("Goat not found: KozaX"));

        mockMvc.perform(put("/lions/Simba")
                        .param("foodName", "KozaX")
                        .param("foodType", "goat"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Goat not found: KozaX"));
    }
}
