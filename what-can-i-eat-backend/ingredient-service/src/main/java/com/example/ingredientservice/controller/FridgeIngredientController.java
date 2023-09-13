package com.example.ingredientservice.controller;


import com.example.ingredientservice.service.FridgeIngredientService;
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
