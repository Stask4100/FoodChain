package com.kornievstas.FoodChain.service;

import com.kornievstas.FoodChain.dto.CowDto;
import com.kornievstas.FoodChain.dto.GoatDto;
import com.kornievstas.FoodChain.entity.Cow;
import com.kornievstas.FoodChain.entity.Goat;
import com.kornievstas.FoodChain.entity.Grass;
import com.kornievstas.FoodChain.exception.AlreadyDeadException;
import com.kornievstas.FoodChain.exception.AlreadyExistsException;
import com.kornievstas.FoodChain.exception.InvalidActionException;
import com.kornievstas.FoodChain.exception.NotFoundException;
import com.kornievstas.FoodChain.mapper.GoatMapper;
import com.kornievstas.FoodChain.repository.GoatRepository;
import com.kornievstas.FoodChain.repository.GrassRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoatService {

    private final GoatRepository goatRepository;
    private final GrassRepository grassRepository;
    private final GoatMapper goatMapper;

    public GoatService(GoatRepository goatRepository, GrassRepository grassRepository, GoatMapper goatMapper) {
        this.goatRepository = goatRepository;
        this.grassRepository = grassRepository;
        this.goatMapper = goatMapper;
    }

    public GoatDto createGoat(String name) {
        if (goatRepository.existsByName(name)) {
            throw new AlreadyExistsException("Goat with name " + name + " already exists");
        }

        Goat goat = new Goat();
        goat.setName(name);
        goat.setAlive(true);

        return goatMapper.toDto(goatRepository.save(goat));
    }

    public List<GoatDto> getAllGoats() {
        return goatRepository.findAll()
                .stream()
                .map(goatMapper::toDto)
                .collect(Collectors.toList());
    }

    public GoatDto getGoat(String name) {
        Goat goat = goatRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Goat not found"));
        return goatMapper.toDto(goat);
    }

    @Transactional
    public GoatDto feedGoatWithGrass(String goatName) {
        Goat goat = goatRepository.findByName(goatName)
                .orElseThrow(() -> new EntityNotFoundException("Goat not found: " + goatName));

        Grass grass = new Grass();
        goat.eatGrass(grass);
        grassRepository.save(grass);
        goatRepository.save(goat);

        return goatMapper.toDto(goat);
    }



}
