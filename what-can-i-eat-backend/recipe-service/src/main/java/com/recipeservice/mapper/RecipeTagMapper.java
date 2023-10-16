package com.recipeservice.mapper;

import com.recipeservice.dto.RecipeTagDto;
import com.recipeservice.model.RecipeTag;

public class RecipeTagMapper {

    public static RecipeTagDto toDto(RecipeTag tag) {
        if (tag == null) return null;
        return new RecipeTagDto(tag.getId(), tag.getTag());
    }

    public static RecipeTag toEntity(RecipeTagDto tagDto) {
        if (tagDto == null) return null;
        RecipeTag tag = new RecipeTag();
        tag.setId(tagDto.id());
        tag.setTag(tagDto.tag());
        return tag;
    }
}
