package com.recipeservice.controller;

import com.recipeservice.service.PreparationStepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PreparationStepController {
    private final PreparationStepService preparationStepService;

    @Autowired
    public PreparationStepController(PreparationStepService preparationStepService) {
        this.preparationStepService = preparationStepService;
    }

}
