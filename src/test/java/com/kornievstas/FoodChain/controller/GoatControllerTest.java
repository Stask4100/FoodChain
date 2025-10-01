package com.kornievstas.FoodChain.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kornievstas.FoodChain.dto.GoatDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class GoatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private GoatDto goat;

    @BeforeEach
    void setUp() {
        goat = GoatDto.builder()
                .name("Goat1")
                .alive(true)
                .build();
    }

    @Test
    void shouldCreateGoat() throws Exception {
        String goatName = "Goat_" + System.currentTimeMillis();
        mockMvc.perform(post("/goats/{name}", goatName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(goatName))
                .andExpect(jsonPath("$.alive").value(true));
    }


    @Test
    void shouldGetAllGoats() throws Exception {
        mockMvc.perform(get("/goats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void shouldGetGoatByName() throws Exception {
        String goatName = "Goat_" + System.currentTimeMillis();
        mockMvc.perform(post("/goats/{name}", goatName))
                .andExpect(status().isOk());

        mockMvc.perform(get("/goats/{name}", goatName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(goatName));
    }

    @Test
    void shouldFeedGoatWithGrass() throws Exception {
        String goatName = "Goat_" + System.currentTimeMillis();
        mockMvc.perform(post("/goats/{name}", goatName))
                .andExpect(status().isOk());

        mockMvc.perform(put("/goats/{name}/eat-grass", goatName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eatenGrassNames", hasSize(1)));
    }

    @Test
    void shouldReturnNotFoundForMissingGoat() throws Exception {
        mockMvc.perform(get("/goats/{name}", "Missing"))
                .andExpect(status().isNotFound());
    }
}
