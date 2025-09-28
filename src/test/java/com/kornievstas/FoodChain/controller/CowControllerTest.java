package com.kornievstas.FoodChain.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kornievstas.FoodChain.dto.CowDto;
import com.kornievstas.FoodChain.exception.NotFoundException;
import com.kornievstas.FoodChain.exception.GlobalExceptionHandler;
import com.kornievstas.FoodChain.service.CowService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CowController.class)
@Import(GlobalExceptionHandler.class)
class CowControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CowService cowService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        reset(cowService);
    }

    @Test
    void createCow_shouldReturnCowDto() throws Exception {
        CowDto dto = new CowDto();
        dto.setId(1L);
        dto.setName("Cow1");
        dto.setAlive(true);
        dto.setEatenGrassNames(List.of());

        when(cowService.createCow("Cow1")).thenReturn(dto);

        mockMvc.perform(post("/cows/Cow1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Cow1"))
                .andExpect(jsonPath("$.alive").value(true))
                .andExpect(jsonPath("$.eatenGrassNames").isArray());
    }

    @Test
    void getAllCows_shouldReturnList() throws Exception {
        CowDto dto = new CowDto();
        dto.setId(1L);
        dto.setName("Cow1");
        dto.setAlive(true);
        dto.setEatenGrassNames(List.of());

        when(cowService.getAllCows()).thenReturn(List.of(dto));

        mockMvc.perform(get("/cows"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Cow1"))
                .andExpect(jsonPath("$[0].alive").value(true));
    }

    @Test
    void getCow_shouldReturnCowDto() throws Exception {
        CowDto dto = new CowDto();
        dto.setId(1L);
        dto.setName("Cow1");
        dto.setAlive(true);
        dto.setEatenGrassNames(List.of());

        when(cowService.getCow("Cow1")).thenReturn(dto);

        mockMvc.perform(get("/cows/Cow1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Cow1"))
                .andExpect(jsonPath("$.alive").value(true));
    }

    @Test
    void feedCowWithGrass_success() throws Exception {
        CowDto updated = new CowDto();
        updated.setId(1L);
        updated.setName("Cow1");
        updated.setAlive(true);
        updated.setEatenGrassNames(List.of("Grass1"));

        when(cowService.feedCowWithGrass("Cow1", "Grass1")).thenReturn(updated);

        mockMvc.perform(put("/cows/Cow1/eat-grass/Grass1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eatenGrassNames[0]").value("Grass1"));
    }

    @Test
    void feedCowWithGrass_notFound_shouldReturn404() throws Exception {
        when(cowService.feedCowWithGrass("Cow1", "Grass1"))
                .thenThrow(new NotFoundException("Grass not found"));

        mockMvc.perform(put("/cows/Cow1/eat-grass/Grass1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Grass not found"));
    }
}
