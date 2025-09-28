package com.kornievstas.FoodChain.service;

import com.kornievstas.FoodChain.dto.GrassDto;
import com.kornievstas.FoodChain.entity.Grass;
import com.kornievstas.FoodChain.exception.AlreadyExistsException;
import com.kornievstas.FoodChain.exception.NotFoundException;
import com.kornievstas.FoodChain.mapper.GrassMapper;
import com.kornievstas.FoodChain.repository.GrassRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GrassService {

    private final GrassRepository grassRepository;
    private final GrassMapper grassMapper;

    public GrassService(GrassRepository grassRepository, GrassMapper grassMapper) {
        this.grassRepository = grassRepository;
        this.grassMapper = grassMapper;
    }

    public GrassDto createGrass(String name) {

        if(grassRepository.existsByName(name)) {
            throw new AlreadyExistsException("Grass with name " + name + " already exists");
        }

        Grass grass = new Grass();
        grass.setName(name);
        grass.setAlive(true);
        return grassMapper.toDto(grassRepository.save(grass));
    }

    public List<GrassDto> getAllGrass() {
        return grassRepository.findAll()
                .stream()
                .map(grassMapper::toDto)
                .collect(Collectors.toList());
    }

    public GrassDto getGrass(String name) {
        Grass grass = grassRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Grass not found"));
        return grassMapper.toDto(grass);
    }
}

