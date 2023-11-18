package com.recipeservice.service;

import com.recipeservice.dto.CreateRecipeDto;
import com.recipeservice.dto.RecipeTagDto;

import java.util.List;
import java.util.Set;

public interface RecipeTagService {
    Set<RecipeTagDto> generateRecipeTags(CreateRecipeDto createRecipeDto, Integer recipeId);
}
