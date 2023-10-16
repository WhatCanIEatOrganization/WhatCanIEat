package com.recipeservice.service;

import com.recipeservice.dto.CreateRecipeDto;
import com.recipeservice.dto.RecipeTagDto;

import java.util.List;

public interface RecipeTagService {
    List<RecipeTagDto> generateRecipeTags(CreateRecipeDto createRecipeDto);
}
