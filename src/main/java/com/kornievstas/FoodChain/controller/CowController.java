package com.kornievstas.FoodChain.controller;

import com.kornievstas.FoodChain.dto.CowDto;
import com.kornievstas.FoodChain.service.CowService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cows")
public class CowController {

    private final CowService cowService;

    public CowController(CowService cowService) {
        this.cowService = cowService;
    }

    @PostMapping("/{name}")
    public CowDto createCow(@PathVariable String name) {
        return cowService.createCow(name);
    }

    @GetMapping
    public List<CowDto> getAllCows() {
        return cowService.getAllCows();
    }

    @GetMapping("/{name}")
    public CowDto getCow(@PathVariable String name) {
        return cowService.getCow(name);
    }

    @PutMapping("/{cowName}/eat-grass/{grassName}")
    public CowDto feedCowWithGrass(@PathVariable String cowName,
                                     @PathVariable String grassName) {
        return cowService.feedCowWithGrass(cowName, grassName);
    }
}
