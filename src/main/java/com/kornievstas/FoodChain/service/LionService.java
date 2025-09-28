package com.kornievstas.FoodChain.service;

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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LionService {

    private final LionRepository lionRepository;
    private final GoatRepository goatRepository;
    private final CowRepository cowRepository;
    private final LionMapper lionMapper;

    public LionService(LionRepository lionRepository,
                       GoatRepository goatRepository,
                       CowRepository cowRepository,
                       LionMapper lionMapper) {
        this.lionRepository = lionRepository;
        this.goatRepository = goatRepository;
        this.cowRepository = cowRepository;
        this.lionMapper = lionMapper;
    }

    public LionDto createLion(String name) {
        if (lionRepository.existsByName(name)) {
            throw new AlreadyExistsException("Lion with name " + name + " already exists");
        }

        Lion lion = new Lion(name);
        Lion savedLion = lionRepository.save(lion);
        return lionMapper.toDto(savedLion);
    }

    public List<LionDto> getAllLions() {
        return lionRepository.findAll()
                .stream()
                .map(lionMapper::toDto)
                .collect(Collectors.toList());
    }

    public LionDto getLion(String name) {
        Lion lion = lionRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Lion not found"));
        return lionMapper.toDto(lion);
    }

    public LionDto feedLionWithGoat(String lionName, String goatName) {
        Lion lion = lionRepository.findByName(lionName)
                .orElseThrow(() -> new NotFoundException("Lion not found"));

        if (!lion.isAlive()) {
            throw new InvalidActionException("Cannot feed dead lion");
        }

        Goat goat = goatRepository.findByName(goatName)
                .orElseThrow(() -> new NotFoundException("Goat not found"));

        if (!goat.isAlive()) {
            throw new AlreadyDeadException("Goat is already dead");
        }

        goat.setAlive(false);
        goat.setEatenByLion(lion);
        lion.getEatenGoats().add(goat);

        goatRepository.save(goat);
        Lion updatedLion = lionRepository.save(lion);
        return lionMapper.toDto(updatedLion);
    }

    public LionDto feedLionWithCow(String lionName, String cowName) {
        Lion lion = lionRepository.findByName(lionName)
                .orElseThrow(() -> new NotFoundException("Lion not found"));

        if (!lion.isAlive()) {
            throw new InvalidActionException("Cannot feed dead lion");
        }

        Cow cow = cowRepository.findByName(cowName)
                .orElseThrow(() -> new NotFoundException("Cow not found"));

        if (!cow.isAlive()) {
            throw new AlreadyDeadException("Cow is already dead");
        }

        cow.setAlive(false);
        cow.setEatenByLion(lion);
        lion.getEatenCows().add(cow);

        cowRepository.save(cow);
        Lion updatedLion = lionRepository.save(lion);
        return lionMapper.toDto(updatedLion);
    }
}
