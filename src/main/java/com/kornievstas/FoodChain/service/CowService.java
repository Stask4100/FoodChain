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
import com.kornievstas.FoodChain.mapper.CowMapper;
import com.kornievstas.FoodChain.repository.CowRepository;
import com.kornievstas.FoodChain.repository.GrassRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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

    @Transactional
    public CowDto feedCowWithGrass(String cowName) {
        Cow cow = cowRepository.findByName(cowName)
                .orElseThrow(() -> new EntityNotFoundException("Cow not found: " + cowName));

        Grass grass = new Grass();
        cow.eatGrass(grass);
        grassRepository.save(grass);
        cowRepository.save(cow);

        return cowMapper.toDto(cow);
    }
}
