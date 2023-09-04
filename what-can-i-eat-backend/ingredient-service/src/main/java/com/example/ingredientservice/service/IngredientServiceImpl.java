package com.example.ingredientservice.service;

import com.example.ingredientservice.dto.IngredientDto;
import com.example.ingredientservice.mapper.IngredientMapper;
import com.example.ingredientservice.model.Ingredient;
import com.example.ingredientservice.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public IngredientDto addNewIngredient(IngredientDto ingredientDto) {
        ingredientRepository.save(IngredientMapper.mapToEntity(ingredientDto));
        return ingredientDto;
    }


    public List<IngredientDto> addNewIngredients(List<IngredientDto> ingredientsDto) {
        List<Ingredient> ingredients = ingredientsDto.stream()
                .map(IngredientMapper::mapToEntity)
                .collect(Collectors.toList());
        ingredientRepository.saveAll(ingredients);
        return ingredients.stream()
                .map(IngredientMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteIngredient(int ingredientId) {
        ingredientRepository.deleteById(ingredientId);
    }

    @Override
    public Optional<IngredientDto> getIngredientById(int ingredientId) {
        Optional<Ingredient> ingredient = ingredientRepository.findById(ingredientId);
        return ingredient.map(IngredientMapper::mapToDto);
    }
    @Override
    public List<IngredientDto> findIngredientsById(List<Integer> ids) {
        List<Ingredient> ingredients = ingredientRepository.findAllById(ids);
        return ingredients.stream().map(IngredientMapper::mapToDto).collect(Collectors.toList());
    }

}
