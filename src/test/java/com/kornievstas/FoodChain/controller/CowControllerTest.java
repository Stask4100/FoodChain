package com.kornievstas.FoodChain.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kornievstas.FoodChain.dto.CowDto;
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
class CowControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private CowDto cow;

    @BeforeEach
    void setUp() throws Exception {
        String cowName = "CowTest";
        cow = CowDto.builder()
                .name(cowName)
                .alive(true)
                .build();
    }

    @Test
    void shouldCreateCow() throws Exception {
        String cowName = "Cow_" + System.currentTimeMillis();
        mockMvc.perform(post("/cows/{name}", cowName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(cowName))
                .andExpect(jsonPath("$.alive").value(true));
    }

    @Test
    void shouldGetAllCows() throws Exception {
        mockMvc.perform(get("/cows"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void shouldGetCowByName() throws Exception {
        String cowName = "Cow_" + System.currentTimeMillis();
        mockMvc.perform(post("/cows/{name}", cowName))
                .andExpect(status().isOk());

        mockMvc.perform(get("/cows/{name}", cowName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(cowName));
    }

    @Test
    void shouldFeedCowWithGrass() throws Exception {
        String cowName = "Cow_" + System.currentTimeMillis();
        mockMvc.perform(post("/cows/{name}", cowName))
                .andExpect(status().isOk());

        mockMvc.perform(put("/cows/{name}/eat-grass", cowName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eatenGrassNames", hasSize(1)));
    }

    @Test
    void shouldReturnNotFoundForMissingCow() throws Exception {
        mockMvc.perform(get("/cows/{name}", "Missing"))
                .andExpect(status().isNotFound());
    }
}
