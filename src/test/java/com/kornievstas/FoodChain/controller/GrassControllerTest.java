package com.kornievstas.FoodChain.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kornievstas.FoodChain.dto.GrassDto;
import com.kornievstas.FoodChain.exception.NotFoundException;
import com.kornievstas.FoodChain.exception.GlobalExceptionHandler;
import com.kornievstas.FoodChain.service.GrassService;
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

@WebMvcTest(controllers = GrassController.class)
@Import(GlobalExceptionHandler.class)
class GrassControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GrassService grassService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        reset(grassService);
    }

    @Test
    void createGrass_shouldReturnGrassDto() throws Exception {
        GrassDto dto = new GrassDto();
        dto.setId(1L);
        dto.setName("Grass1");

        when(grassService.createGrass("Grass1")).thenReturn(dto);

        mockMvc.perform(post("/grass/Grass1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Grass1"));
    }

    @Test
    void getAllGrass_shouldReturnList() throws Exception {
        GrassDto dto = new GrassDto();
        dto.setId(1L);
        dto.setName("Grass1");

        when(grassService.getAllGrass()).thenReturn(List.of(dto));

        mockMvc.perform(get("/grass"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Grass1"));
    }

    @Test
    void getGrass_shouldReturnGrassDto() throws Exception {
        GrassDto dto = new GrassDto();
        dto.setId(1L);
        dto.setName("Grass1");

        when(grassService.getGrass("Grass1")).thenReturn(dto);

        mockMvc.perform(get("/grass/Grass1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Grass1"));
    }

    @Test
    void getGrass_notFound_shouldReturn404() throws Exception {
        when(grassService.getGrass("Grass2"))
                .thenThrow(new NotFoundException("Grass not found"));

        mockMvc.perform(get("/grass/Grass2"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Grass not found"));
    }
}
