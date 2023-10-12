package com.example.ingredientservice.controller;

import com.example.ingredientservice.dto.FridgeIngredientDto;
import com.example.ingredientservice.service.FridgeIngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/fridge")
@CrossOrigin
public class FridgeController {

    private final FridgeIngredientService ingredientService;

    @Autowired
    public FridgeController(FridgeIngredientService ingredientService) {
        this.ingredientService = ingredientService;
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
