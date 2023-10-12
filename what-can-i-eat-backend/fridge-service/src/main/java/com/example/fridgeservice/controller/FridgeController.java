package com.example.fridgeservice.controller;


import com.example.fridgeservice.dto.BasicIngredientDto;
import com.example.fridgeservice.dto.FridgeIngredientDto;
import com.example.fridgeservice.service.FridgeIngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fridge")
public class FridgeController {

    private final FridgeIngredientService ingredientService;

    @Autowired
    public FridgeController(FridgeIngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }


    @GetMapping("/search")
    public ResponseEntity<BasicIngredientDto> searchIngredient(@RequestParam String name) {
        return ingredientService.searchBasicIngredient(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<FridgeIngredientDto> addFridgeIngredient(@RequestBody FridgeIngredientDto fridgeIngredientDto) {
        if(fridgeIngredientDto == null) {
            return ResponseEntity.badRequest().build();
        }
        FridgeIngredientDto fridgeIngredient = ingredientService.addFridgeIngredient(fridgeIngredientDto);
        return ResponseEntity.ok(fridgeIngredient);
    }

    @GetMapping
    public ResponseEntity<List<FridgeIngredientDto>> getAllFridgeIngredients(){
        List<FridgeIngredientDto> fridgeIngredientDtoList = ingredientService.getAllFridgeIngredients();
        return ResponseEntity.ok(fridgeIngredientDtoList);
    }
}
