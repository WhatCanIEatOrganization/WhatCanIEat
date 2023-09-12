package com.example.ingredientservice.service;

import com.example.ingredientservice.dto.BasicIngredientDto;

import java.util.Optional;

public interface BasicIngredientService {
    Optional<BasicIngredientDto> getBasicIngredientById(int id);
}
