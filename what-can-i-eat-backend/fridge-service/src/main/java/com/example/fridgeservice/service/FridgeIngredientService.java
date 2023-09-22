package com.example.fridgeservice.service;

import com.example.fridgeservice.dto.BasicIngredientDto;

import java.util.Optional;

public interface FridgeIngredientService {
    Optional<BasicIngredientDto> searchIngredient(String name);
}
