package com.kornievstas.FoodChain.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kornievstas.FoodChain.dto.LionDto;
import com.kornievstas.FoodChain.exception.NotFoundException;
import com.kornievstas.FoodChain.exception.GlobalExceptionHandler;
import com.kornievstas.FoodChain.service.LionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
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

@WebMvcTest(controllers = LionController.class)
@Import(GlobalExceptionHandler.class)
class LionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LionService lionService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        reset(lionService);
    }

    @Test
    void createLion_shouldReturnLionDto() throws Exception {
        LionDto dto = new LionDto();
        dto.setId(1L);
        dto.setName("Simba");
        dto.setAlive(true);
        dto.setEatenGoats(List.of());
        dto.setEatenCows(List.of());

        when(lionService.createLion("Simba")).thenReturn(dto);

        mockMvc.perform(post("/lions/Simba"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Simba"))
                .andExpect(jsonPath("$.alive").value(true))
                .andExpect(jsonPath("$.eatenGoats").isArray())
                .andExpect(jsonPath("$.eatenCows").isArray());
    }

    @Test
    void getAllLions_shouldReturnList() throws Exception {
        LionDto dto = new LionDto();
        dto.setId(1L);
        dto.setName("Simba");
        dto.setAlive(true);
        dto.setEatenGoats(List.of());
        dto.setEatenCows(List.of());

        when(lionService.getAllLions()).thenReturn(List.of(dto));

        mockMvc.perform(get("/lions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Simba"))
                .andExpect(jsonPath("$[0].alive").value(true));
    }

    @Test
    void feedLion_withGoat_success() throws Exception {
        LionDto updated = new LionDto();
        updated.setId(1L);
        updated.setName("Simba");
        updated.setAlive(true);
        updated.setEatenGoats(List.of("Koza2"));
        updated.setEatenCows(List.of());

        when(lionService.feedLionWithGoat("Simba", "Koza2")).thenReturn(updated);

        mockMvc.perform(put("/lions/Simba")
                        .param("foodName", "Koza2")
                        .param("foodType", "goat"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eatenGoats[0]").value("Koza2"));
    }

    @Test
    void feedLion_withGoat_notFound_shouldReturn404() throws Exception {
        // кидатимемо NotFoundException з сервісу
        when(lionService.feedLionWithGoat("Simba", "Koza2"))
                .thenThrow(new NotFoundException("Goat not found"));

        mockMvc.perform(put("/lions/Simba")
                        .param("foodName", "Koza2")
                        .param("foodType", "goat"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Goat not found"));
    }
}
