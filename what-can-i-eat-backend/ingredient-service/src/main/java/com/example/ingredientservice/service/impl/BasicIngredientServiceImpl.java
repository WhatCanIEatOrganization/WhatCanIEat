package com.example.ingredientservice.service.impl;

import com.example.ingredientservice.dto.BasicIngredientDto;
import com.example.ingredientservice.mapper.BasicIngredientMapper;
import com.example.ingredientservice.mapper.RecipeIngredientMapper;
import com.example.ingredientservice.model.BasicIngredient;
import com.example.ingredientservice.repository.BasicIngredientRepository;
import com.example.ingredientservice.service.BasicIngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


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
}
