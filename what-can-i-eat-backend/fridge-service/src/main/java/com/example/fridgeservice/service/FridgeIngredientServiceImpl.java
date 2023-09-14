package com.example.fridgeservice.service;


import com.example.fridgeservice.repository.FridgeIngredientRepository;
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
