package com.example.ingredientservice.service;

import com.example.ingredientservice.dto.BasicIngredientDto;
import com.example.ingredientservice.dto.RecipeIngredientDto;

import java.util.List;
import java.util.Optional;

public interface BasicIngredientService {
    Optional<BasicIngredientDto> getBasicIngredientById(int id);
    Optional<BasicIngredientDto> getBasicIngredientByName(String name);
    List<BasicIngredientDto> getAllBasicIngredients();

}
