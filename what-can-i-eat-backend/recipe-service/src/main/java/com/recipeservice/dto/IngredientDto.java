package com.recipeservice.dto;

import java.io.Serializable;

public record IngredientDto(Integer id, String name, String description, String imageUrl, String amountWithUnit) implements Serializable {

    @Override
    public String toString() {
        return "IngredientDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", amountWithUnit='" + amountWithUnit + '\'' +
                '}';
    }
}