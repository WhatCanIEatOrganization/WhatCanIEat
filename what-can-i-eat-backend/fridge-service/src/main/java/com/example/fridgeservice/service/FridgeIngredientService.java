package com.example.fridgeservice.service;

import com.example.fridgeservice.dto.BasicIngredientDto;
import com.example.fridgeservice.dto.FridgeIngredientDto;

import java.util.List;
import java.util.Optional;

public interface FridgeIngredientService {
    Optional<BasicIngredientDto> searchBasicIngredient(String name);
    FridgeIngredientDto addFridgeIngredient(FridgeIngredientDto fridgeIngredientDto);
    List<FridgeIngredientDto> getAllFridgeIngredients();
}
