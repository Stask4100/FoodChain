package com.kornievstas.FoodChain.controller;

import com.kornievstas.FoodChain.dto.GoatDto;
import com.kornievstas.FoodChain.service.GoatService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goats")
public class GoatController {

    private final GoatService goatService;

    public GoatController(GoatService goatService) {
        this.goatService = goatService;
    }

    @PostMapping("/{name}")
    public GoatDto createGoat(@PathVariable String name) {
        return goatService.createGoat(name);
    }

    @GetMapping
    public List<GoatDto> getAllGoats() {
        return goatService.getAllGoats();
    }

    @GetMapping("/{name}")
    public GoatDto getGoat(@PathVariable String name) {
        return goatService.getGoat(name);
    }

    @PutMapping("/{goatName}/eat-grass/{grassName}")
    public GoatDto feedGoatWithGrass(@PathVariable String goatName,
                                     @PathVariable String grassName) {
        return goatService.feedGoatWithGrass(goatName, grassName);
    }

}
