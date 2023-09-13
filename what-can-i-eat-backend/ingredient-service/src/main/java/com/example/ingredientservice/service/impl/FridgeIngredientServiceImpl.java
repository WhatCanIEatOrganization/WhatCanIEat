package com.example.ingredientservice.service.impl;


import com.example.ingredientservice.repository.FridgeIngredientRepository;
import com.example.ingredientservice.service.FridgeIngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FridgeIngredientServiceImpl implements FridgeIngredientService {
    private final FridgeIngredientRepository ingredientRepository;

    @Autowired
    public FridgeIngredientServiceImpl(FridgeIngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }
}
