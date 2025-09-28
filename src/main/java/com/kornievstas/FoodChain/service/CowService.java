package com.kornievstas.FoodChain.service;

import com.kornievstas.FoodChain.dto.CowDto;
import com.kornievstas.FoodChain.entity.Cow;
import com.kornievstas.FoodChain.entity.Grass;
import com.kornievstas.FoodChain.exception.AlreadyDeadException;
import com.kornievstas.FoodChain.exception.AlreadyExistsException;
import com.kornievstas.FoodChain.exception.InvalidActionException;
import com.kornievstas.FoodChain.exception.NotFoundException;
import com.kornievstas.FoodChain.mapper.CowMapper;
import com.kornievstas.FoodChain.repository.CowRepository;
import com.kornievstas.FoodChain.repository.GrassRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CowService {

    private final CowRepository cowRepository;
    private final GrassRepository grassRepository;
    private final CowMapper cowMapper;

    public CowService(CowRepository cowRepository, GrassRepository grassRepository, CowMapper cowMapper) {
        this.cowRepository = cowRepository;
        this.grassRepository = grassRepository;
        this.cowMapper = cowMapper;
    }

    public CowDto createCow(String name) {
        if (cowRepository.existsByName(name)) {
            throw new AlreadyExistsException("Cow with name " + name + " already exists");
        }

        Cow cow = new Cow();
        cow.setName(name);
        cow.setAlive(true);

        return cowMapper.toDto(cowRepository.save(cow));
    }

    public List<CowDto> getAllCows() {
        return cowRepository.findAll()
                .stream()
                .map(cowMapper::toDto)
                .collect(Collectors.toList());
    }

    public CowDto getCow(String name) {
        Cow cow = cowRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Cow not found"));
        return cowMapper.toDto(cow);
    }

    public CowDto feedCowWithGrass(String cowName, String grassName) {
        Cow cow = cowRepository.findByName(cowName)
                .orElseThrow(() -> new NotFoundException("Cow not found"));

        if (!cow.isAlive()) {
            throw new InvalidActionException("Cannot feed dead cow");
        }

        Grass grass = grassRepository.findByName(grassName)
                .orElseThrow(() -> new NotFoundException("Grass not found"));

        if (!grass.isAlive()) {
            throw new AlreadyDeadException("Grass is already eaten");
        }

        cow.eatGrass(grass);
        grass.setAlive(false);
        grassRepository.save(grass);

        return cowMapper.toDto(cowRepository.save(cow));
    }
}
