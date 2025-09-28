package com.kornievstas.FoodChain.controller;

import com.kornievstas.FoodChain.dto.LionDto;
import com.kornievstas.FoodChain.service.LionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lions")
public class LionController {

    private final LionService lionService;

    public LionController(LionService lionService) {
        this.lionService = lionService;
    }

    @PostMapping("/{name}")
    public LionDto createLion(@PathVariable String name) {
        return lionService.createLion(name);
    }

    @GetMapping
    public List<LionDto> getAllLions() {
        return lionService.getAllLions();
    }

    @GetMapping("/{name}")
    public LionDto getLion(@PathVariable String name) {
        return lionService.getLion(name);
    }

    // PUT http://localhost:8080/lions/Lion1?foodName=Koza1&foodType=goat
    @PutMapping("/{lionName}")
    public LionDto feedLion(@PathVariable String lionName,
                            @RequestParam String foodName,
                            @RequestParam String foodType) {

        // Ми перевіряємо, який саме тип їжі передав користувач (коза чи корова).
        // Якщо foodType = "goat", викликаємо метод сервісу, який годує лева козою.
        // Якщо foodType = "cow", викликаємо метод сервісу, який годує лева коровою.
        // Якщо foodType невідомий — кидаємо помилку.
        switch (foodType.toLowerCase()) {
            case "goat":
                return lionService.feedLionWithGoat(lionName, foodName);
            case "cow":
                return lionService.feedLionWithCow(lionName, foodName);
            default:
                throw new RuntimeException("Unsupported food type: " + foodType);
        }
    }
}
