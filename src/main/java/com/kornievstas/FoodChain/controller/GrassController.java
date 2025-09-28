package com.kornievstas.FoodChain.controller;

import com.kornievstas.FoodChain.dto.GrassDto;
import com.kornievstas.FoodChain.service.GrassService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grass")
public class GrassController {

    private final GrassService grassService;

    public GrassController(GrassService grassService) {
        this.grassService = grassService;
    }

    @PostMapping("/{name}")
    public GrassDto createGrass(@PathVariable String name) {
        return grassService.createGrass(name);
    }

    @GetMapping
    public List<GrassDto> getAllGrass() {
        return grassService.getAllGrass();
    }

    @GetMapping("/{name}")
    public GrassDto getGrass(@PathVariable String name) {
        return grassService.getGrass(name);
    }
}
