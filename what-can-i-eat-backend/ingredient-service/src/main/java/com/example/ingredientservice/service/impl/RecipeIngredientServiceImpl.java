package com.example.ingredientservice.service.impl;

import com.example.ingredientservice.dto.RecipeIngredientDto;
import com.example.ingredientservice.mapper.RecipeIngredientMapper;
import com.example.ingredientservice.model.RecipeIngredient;
import com.example.ingredientservice.repository.RecipeIngredientRepository;
import com.example.ingredientservice.service.RecipeIngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipeIngredientServiceImpl implements RecipeIngredientService {

    private final RecipeIngredientRepository recipeIngredientRepository;

    @Autowired
    public RecipeIngredientServiceImpl(RecipeIngredientRepository recipeIngredientRepository) {
        this.recipeIngredientRepository = recipeIngredientRepository;
    }

    @Override
    public RecipeIngredientDto addNewIngredient(RecipeIngredientDto recipeIngredientDto) {
        recipeIngredientRepository.save(RecipeIngredientMapper.mapToEntity(recipeIngredientDto));
        return recipeIngredientDto;
    }

    @Override
    public List<RecipeIngredientDto> addNewIngredients(List<RecipeIngredientDto> ingredientsDto) {
        List<RecipeIngredient> recipeIngredients = ingredientsDto.stream()
                .map(RecipeIngredientMapper::mapToEntity)
                .collect(Collectors.toList());
        recipeIngredientRepository.saveAll(recipeIngredients);
        return recipeIngredients.stream()
                .map(RecipeIngredientMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteIngredient(int ingredientId) {
        recipeIngredientRepository.deleteById(ingredientId);
    }

    @Override
    @Cacheable(value = "ingredients", key = "#ingredientId")
    public Optional<RecipeIngredientDto> getIngredientById(int ingredientId) {
        Optional<RecipeIngredient> ingredient = recipeIngredientRepository.findById(ingredientId);
        return ingredient.map(RecipeIngredientMapper::mapToDto);
    }
    @Override
    @Cacheable(value = "recipeIngredients", key = "#ids.toString()")
    public List<RecipeIngredientDto> findIngredientsById(List<Integer> ids) {
        List<RecipeIngredient> recipeIngredients = recipeIngredientRepository.findAllById(ids);
        return recipeIngredients.stream().map(RecipeIngredientMapper::mapToDto).collect(Collectors.toList());
    }

}
