package com.example.ingredientservice.service.impl;

import com.example.ingredientservice.dto.BasicIngredientDto;
import com.example.ingredientservice.mapper.BasicIngredientMapper;
import com.example.ingredientservice.model.BasicIngredient;
import com.example.ingredientservice.repository.BasicIngredientRepository;
import com.example.ingredientservice.service.BasicIngredientService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Optional<BasicIngredientDto> getBasicIngredientById(int id) {
        Optional<BasicIngredient> ingredient = ingredientRepository.findById(id);
        return ingredient.map(BasicIngredientMapper::entityToDto);
    }

    @Override
    public Optional<BasicIngredientDto> getBasicIngredientByName(String name) {
        Optional<BasicIngredient> ingredient = ingredientRepository.findBasicIngredientByName(name);
        return ingredient.map(BasicIngredientMapper::entityToDto);
    }

    @Override
    public List<BasicIngredientDto> getAllBasicIngredients() {
        // hardcoded limit for tests
        return ingredientRepository
                .findAll()
                .stream()
                .map(BasicIngredientMapper::entityToDto)
                .collect(Collectors.toList());
    }
}
