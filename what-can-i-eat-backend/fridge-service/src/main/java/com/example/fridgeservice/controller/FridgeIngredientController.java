package com.example.fridgeservice.controller;


import com.example.fridgeservice.service.FridgeIngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class FridgeIngredientController {

    private final FridgeIngredientService ingredientService;

    @Autowired
    public FridgeIngredientController(FridgeIngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }
}
