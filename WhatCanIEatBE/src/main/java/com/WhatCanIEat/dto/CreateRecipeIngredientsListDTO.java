package com.WhatCanIEat.dto;

import com.WhatCanIEat.model.Ingredient;


import java.util.Set;

public record CreateRecipeIngredientsListDTO(long recipeId, Set<Ingredient> ingredientsList) {
}
