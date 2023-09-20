package com.example.ingredientservice.controller;

import com.example.ingredientservice.dto.BasicIngredientDto;
import com.example.ingredientservice.service.BasicIngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/ingredient")
public class BasicIngredientController {

    private final BasicIngredientService ingredientService;

    @Autowired
    public BasicIngredientController(BasicIngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }


    @GetMapping("/{ingredientId}")
    public ResponseEntity<?> getRecipeIngredientById(@PathVariable int ingredientId){
        Optional<BasicIngredientDto> ingredient = ingredientService.getBasicIngredientById(ingredientId);
        if(ingredient.isPresent()){
            return ResponseEntity
                    .status(HttpStatus.FOUND)
                    .body(ingredient.get());
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @GetMapping("/name/{ingredientName}")
    public ResponseEntity<?> getBasicIngredientByName(@PathVariable String ingredientName){
        Optional<BasicIngredientDto> ingredient = ingredientService.getBasicIngredientByName(ingredientName);
        if(ingredient.isPresent()){
            return ResponseEntity
                    .status(HttpStatus.FOUND)
                    .body(ingredient.get());
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<String> getHardcodedIngredients() {
        String json = "[{ \"id\": 21, \"name\": \"Asparagus\", \"amount\": 100, \"unitMeasure\": \"g\" }, { \"id\": 22, \"name\": \"Carrot\", \"amount\": 200, \"unitMeasure\": \"g\" }]";
        return new ResponseEntity<>(json, HttpStatus.OK);
    }
}
