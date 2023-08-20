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
        Ingredient ingredient = ingredientRepository.save(IngredientMapper.mapToEntity(ingredientDto));
        return ingredientDto;
    }

    @Override
    public List<IngredientDto> getIngredientList() {
        return ingredientRepository
                .findAll()
                .stream()
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

}
