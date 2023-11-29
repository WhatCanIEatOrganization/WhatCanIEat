package com.recipeservice.service.impl;

import com.recipeservice.dto.CreateRecipeDto;
import com.recipeservice.dto.IngredientDto;
import com.recipeservice.dto.RecipeTagDto;
import com.recipeservice.mapper.RecipeTagMapper;
import com.recipeservice.repository.RecipeTagRepository;
import com.recipeservice.service.RecipeTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RecipeTagServiceImpl implements RecipeTagService {
    private final RecipeTagRepository recipeTagRepository;

    @Autowired
    public RecipeTagServiceImpl(RecipeTagRepository recipeTagRepository) {
        this.recipeTagRepository = recipeTagRepository;
    }

    @Override
    public Set<RecipeTagDto> generateRecipeTags(CreateRecipeDto createRecipeDto, Integer recipeId) {
        return extractTags(createRecipeDto)
                .stream()
                .map(tag -> RecipeTagMapper.toEntity(new RecipeTagDto(null, tag), recipeId))
                .map(recipeTagRepository::save)
                .map(RecipeTagMapper::toDto)
                .collect(Collectors.toSet());
    }

    private List<String> extractTags(CreateRecipeDto createRecipeDto) {
        List<String> tags = createRecipeDto.ingredients()
                .stream()
                .map(IngredientDto::name)
                .collect(Collectors.toList());
        tags.addAll(Arrays.asList(createRecipeDto.name().split(" ")));
        tags.forEach(System.out::println);
        return tags;
    }


}
