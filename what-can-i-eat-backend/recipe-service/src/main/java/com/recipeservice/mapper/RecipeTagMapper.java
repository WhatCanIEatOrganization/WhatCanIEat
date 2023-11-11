package com.recipeservice.mapper;

import com.recipeservice.dto.RecipeTagDto;
import com.recipeservice.model.Recipe;
import com.recipeservice.model.RecipeTag;

public class RecipeTagMapper {

    public static RecipeTagDto toDto(RecipeTag tag) {
        if (tag == null) return null;
        return new RecipeTagDto(tag.getId(), tag.getTag());
    }

    public static RecipeTag toEntity(RecipeTagDto tagDto, Integer recipeId) {
        if (tagDto == null) return null;
        RecipeTag tag = new RecipeTag();
        tag.setTag(tagDto.tag());

        if (recipeId != null) {
            Recipe recipeReference = new Recipe();
            recipeReference.setId(recipeId);
            tag.setRecipe(recipeReference);
        }

        return tag;
    }
}
