package com.example.ingredientservice.service.impl;

import com.example.ingredientservice.dto.BasicIngredientDto;
import com.example.ingredientservice.mapper.BasicIngredientMapper;
import com.example.ingredientservice.model.BasicIngredient;
import com.example.ingredientservice.repository.BasicIngredientRepository;
import com.example.ingredientservice.service.BasicIngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class BasicIngredientServiceImpl implements BasicIngredientService {

    private final BasicIngredientRepository ingredientRepository;

    @Autowired
    public BasicIngredientServiceImpl(BasicIngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    @Cacheable(value = "basicIngredients", key = "#id")
    public Optional<BasicIngredientDto> getBasicIngredientById(int id) {
        return ingredientRepository.findById(id)
                .map(BasicIngredientMapper::entityToDto);
    }

    @Override
    @Cacheable(value = "basicIngredients", key = "#name")
    public Optional<BasicIngredientDto> getBasicIngredientByName(String name) {
        return ingredientRepository.findBasicIngredientByName(name)
                .map(BasicIngredientMapper::entityToDto);
    }


    @Override
    @Cacheable(value = "basicIngredients", key = "'allIngredients'")
    public List<BasicIngredientDto> getAllBasicIngredients() {
        return ingredientRepository.findAll()
                .stream()
                .map(BasicIngredientMapper::entityToDto)
                .collect(Collectors.toList());
    }

}
