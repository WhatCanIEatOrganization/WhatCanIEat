package com.example.fridgeservice.controller;


import com.example.fridgeservice.dto.BasicIngredientDto;
import com.example.fridgeservice.service.FridgeIngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fridge")
public class FridgeController {

    private final FridgeIngredientService ingredientService;

    @Autowired
    public FridgeController(FridgeIngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }


    @GetMapping("/search")
    public ResponseEntity<BasicIngredientDto> searchIngredient(@RequestParam String name) {
        return ingredientService.searchIngredient(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
