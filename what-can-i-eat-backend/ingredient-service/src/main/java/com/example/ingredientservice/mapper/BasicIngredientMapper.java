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

    // do poprawy gdy legacy null to imageurl tez null
    public static BasicIngredientDto entityToDto(BasicIngredient entity) {
        Integer legacyId = null;
        if (entity.getLegacyId() != null) {
            legacyId = entity.getLegacyId();
        }
        return new BasicIngredientDto(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                legacyId,
                null
        );
    }
}