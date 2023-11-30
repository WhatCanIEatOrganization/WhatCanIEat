package com.recipeservice.dto;

import java.io.Serializable;

public record IngredientDto(int id, String name, String description, String imageUrl, String amountWithUnit) implements Serializable {
}