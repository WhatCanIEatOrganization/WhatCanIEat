package com.recipeservice.dto;

import java.io.Serializable;

public record RecipeTagDto(Integer id, String tag) implements Serializable {
}
