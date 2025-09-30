package com.kornievstas.FoodChain.service.implementation;

import com.kornievstas.FoodChain.dto.LionDto;
import com.kornievstas.FoodChain.entity.Cow;
import com.kornievstas.FoodChain.entity.Goat;
import com.kornievstas.FoodChain.entity.Lion;
import com.kornievstas.FoodChain.exception.AlreadyDeadException;
import com.kornievstas.FoodChain.exception.AlreadyExistsException;
import com.kornievstas.FoodChain.exception.InvalidActionException;
import com.kornievstas.FoodChain.exception.NotFoundException;
import com.kornievstas.FoodChain.mapper.LionMapper;
import com.kornievstas.FoodChain.repository.CowRepository;
import com.kornievstas.FoodChain.repository.GoatRepository;
import com.kornievstas.FoodChain.repository.LionRepository;
import com.kornievstas.FoodChain.service.LionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LionServiceImpl implements LionService {

    private final LionRepository lionRepository;
    private final GoatRepository goatRepository;
    private final CowRepository cowRepository;
    private final LionMapper lionMapper;

    @Override
    public LionDto createLion(String name) {
        if (lionRepository.existsByName(name)) {
            throw new AlreadyExistsException("Lion with name " + name + " already exists");
        }

        Lion lion = new Lion(name);
        return lionMapper.toDto(lionRepository.save(lion));
    }

    @Override
    public List<LionDto> getAllLions() {
        return lionRepository.findAll()
                .stream()
                .map(lionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public LionDto getLion(String name) {
        Lion lion = lionRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Lion not found: " + name));
        return lionMapper.toDto(lion);
    }

    @Override
    public LionDto feedLionWithGoat(String lionName, String goatName) {
        Lion lion = findAliveLionByName(lionName);
        Goat goat = findAliveGoatByName(goatName);

        goat.setAlive(false);
        goat.setEatenByLion(lion);
        lion.getEatenGoats().add(goat);

        goatRepository.save(goat);
        return lionMapper.toDto(lionRepository.save(lion));
    }

    @Override
    public LionDto feedLionWithCow(String lionName, String cowName) {
        Lion lion = findAliveLionByName(lionName);
        Cow cow = findAliveCowByName(cowName);

        cow.setAlive(false);
        cow.setEatenByLion(lion);
        lion.getEatenCows().add(cow);

        cowRepository.save(cow);
        return lionMapper.toDto(lionRepository.save(lion));
    }

    @Override
    public LionDto feedLion(String lionName, String foodName, String foodType) {
        switch (foodType.toLowerCase()) {
            case "goat":
                return feedLionWithGoat(lionName, foodName);
            case "cow":
                return feedLionWithCow(lionName, foodName);
            default:
                throw new InvalidActionException("Unsupported food type: " + foodType);
        }
    }

    private Lion findAliveLionByName(String lionName) {
        Lion lion = lionRepository.findByName(lionName)
                .orElseThrow(() -> new NotFoundException("Lion not found: " + lionName));

        if (!lion.isAlive()) {
            throw new InvalidActionException("Cannot feed dead lion: " + lionName);
        }
        return lion;
    }

    private Goat findAliveGoatByName(String goatName) {
        Goat goat = goatRepository.findByName(goatName)
                .orElseThrow(() -> new NotFoundException("Goat not found: " + goatName));

        if (!goat.isAlive()) {
            throw new AlreadyDeadException("Goat is already dead: " + goatName);
        }
        return goat;
    }

    private Cow findAliveCowByName(String cowName) {
        Cow cow = cowRepository.findByName(cowName)
                .orElseThrow(() -> new NotFoundException("Cow not found: " + cowName));

        if (!cow.isAlive()) {
            throw new AlreadyDeadException("Cow is already dead: " + cowName);
        }
        return cow;
    }
}
