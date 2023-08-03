package com.recipeservice.dto;

import com.recipeservice.model.Ingredient;


import java.util.Set;

public record CreateRecipeIngredientsListDTO(long recipeId, Set<Ingredient> ingredientsList) {
}
