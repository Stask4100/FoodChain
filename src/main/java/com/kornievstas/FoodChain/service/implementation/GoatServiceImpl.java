package com.kornievstas.FoodChain.service.implementation;

import com.kornievstas.FoodChain.dto.GoatDto;
import com.kornievstas.FoodChain.entity.Goat;
import com.kornievstas.FoodChain.entity.Grass;
import com.kornievstas.FoodChain.exception.AlreadyExistsException;
import com.kornievstas.FoodChain.exception.NotFoundException;
import com.kornievstas.FoodChain.mapper.GoatMapper;
import com.kornievstas.FoodChain.repository.GoatRepository;
import com.kornievstas.FoodChain.repository.GrassRepository;
import com.kornievstas.FoodChain.service.GoatService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GoatServiceImpl implements GoatService {

    private final GoatRepository goatRepository;
    private final GrassRepository grassRepository;
    private final GoatMapper goatMapper;

    @Override
    public GoatDto createGoat(String name) {
        if (goatRepository.existsByName(name)) {
            throw new AlreadyExistsException("Goat with name " + name + " already exists");
        }

        Goat goat = new Goat();
        goat.setName(name);
        goat.setAlive(true);

        return goatMapper.toDto(goatRepository.save(goat));
    }

    @Override
    public List<GoatDto> getAllGoats() {
        return goatRepository.findAll()
                .stream()
                .map(goatMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public GoatDto getGoat(String name) {
        Goat goat = goatRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Goat not found: " + name));
        return goatMapper.toDto(goat);
    }

    @Override
    @Transactional
    public GoatDto feedGoatWithGrass(String goatName) {
        Goat goat = findAliveGoatByName(goatName);

        Grass grass = new Grass();
        goat.eatGrass(grass);

        grassRepository.save(grass);
        return goatMapper.toDto(goatRepository.save(goat));
    }

    private Goat findAliveGoatByName(String goatName) {
        Goat goat = goatRepository.findByName(goatName)
                .orElseThrow(() -> new NotFoundException("Goat not found: " + goatName));

        if (!goat.isAlive()) {
            throw new IllegalStateException("Cannot feed dead goat: " + goatName);
        }
        return goat;
    }
}
