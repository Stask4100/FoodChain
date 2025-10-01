package com.kornievstas.FoodChain.service.implementation;

import com.kornievstas.FoodChain.dto.CowDto;
import com.kornievstas.FoodChain.entity.Cow;
import com.kornievstas.FoodChain.entity.Grass;
import com.kornievstas.FoodChain.exception.AlreadyExistsException;
import com.kornievstas.FoodChain.exception.InvalidActionException;
import com.kornievstas.FoodChain.exception.NotFoundException;
import com.kornievstas.FoodChain.mapper.CowMapper;
import com.kornievstas.FoodChain.repository.CowRepository;
import com.kornievstas.FoodChain.repository.GrassRepository;
import com.kornievstas.FoodChain.service.CowService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CowServiceImpl implements CowService {

    private final CowRepository cowRepository;
    private final GrassRepository grassRepository;
    private final CowMapper cowMapper;

    @Override
    public CowDto createCow(String name) {
        if (cowRepository.existsByName(name)) {
            throw new AlreadyExistsException("Cow with name " + name + " already exists");
        }

        Cow cow = new Cow();
        cow.setName(name);
        cow.setAlive(true);

        return cowMapper.toDto(cowRepository.save(cow));
    }

    @Override
    public List<CowDto> getAllCows() {
        return cowRepository.findAll()
                .stream()
                .map(cowMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CowDto getCow(String name) {
        Cow cow = cowRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Cow not found: " + name));
        return cowMapper.toDto(cow);
    }

    @Override
    @Transactional
    public CowDto feedCowWithGrass(String cowName) {
        Cow cow = findAliveCowByName(cowName);

        Grass grass = new Grass();
        cow.eatGrass(grass);

        grassRepository.save(grass);
        return cowMapper.toDto(cowRepository.save(cow));
    }

    private Cow findAliveCowByName(String cowName) {
        Cow cow = cowRepository.findByName(cowName)
                .orElseThrow(() -> new NotFoundException("Cow not found: " + cowName));

        if (!cow.isAlive()) {
            throw new InvalidActionException("Cannot feed dead cow: " + cowName);
        }
        return cow;
    }

}
