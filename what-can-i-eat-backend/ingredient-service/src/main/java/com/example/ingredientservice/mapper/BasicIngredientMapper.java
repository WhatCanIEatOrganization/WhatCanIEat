package com.example.ingredientservice.mapper;

import com.example.ingredientservice.dto.BasicIngredientDto;
import com.example.ingredientservice.model.BasicIngredient;

public class BasicIngredientMapper {

    public static BasicIngredient dtoToEntity(BasicIngredientDto dto) {
        BasicIngredient entity = new BasicIngredient();
        entity.setId(dto.id());
        entity.setName(dto.name());
        entity.setDescription(dto.description());
        entity.setLegacyId(dto.legacyId());
        return entity;
    }

    public static BasicIngredientDto entityToDto(BasicIngredient entity) {
        return new BasicIngredientDto(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getLegacyId(),
                null
        );
    }
}