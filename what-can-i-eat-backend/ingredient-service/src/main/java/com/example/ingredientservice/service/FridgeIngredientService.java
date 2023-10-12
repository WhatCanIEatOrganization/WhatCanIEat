package com.example.ingredientservice.service;


import com.example.ingredientservice.dto.FridgeIngredientDto;

import java.util.List;

public interface FridgeIngredientService {
    FridgeIngredientDto addFridgeIngredient(FridgeIngredientDto fridgeIngredientDto);
    List<FridgeIngredientDto> getAllFridgeIngredients();
}
