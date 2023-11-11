package com.recipeservice.controller;

import com.recipeservice.dto.PreparationStepDto;
import com.recipeservice.service.PreparationStepService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/prep-steps")
@CrossOrigin
public class PreparationStepController {

    private static final Logger logger = LoggerFactory.getLogger(PreparationStepController.class);
    private final PreparationStepService preparationStepService;

    @Autowired
    public PreparationStepController(PreparationStepService preparationStepService) {
        this.preparationStepService = preparationStepService;
    }

    @GetMapping("/{recipeId}")
    @Operation(summary = "Get preparation steps by recipe ID", description = "Returns a list of preparation steps for a given recipe.")
    public ResponseEntity<List<PreparationStepDto>> getPreparationStepsByRecipeId(@PathVariable int recipeId) {
        logger.info("Attempting to retrieve preparation steps for recipe ID: {}", recipeId);
        List<PreparationStepDto> preparationStepDto = preparationStepService.getPreparationStepsByRecipeId(recipeId);
        logger.info("Retrieved {} preparation steps for recipe ID: {}", preparationStepDto.size(), recipeId);
        return ResponseEntity
                .status(HttpStatus.OK)  // Should be HttpStatus.OK instead of HttpStatus.FOUND
                .body(preparationStepDto);
    }

    @DeleteMapping("/{preparationStepId}")
    @Operation(summary = "Delete a preparation step", description = "Deletes a specific preparation step based on its ID.")
    public ResponseEntity<Void> deletePreparationStep(@PathVariable int preparationStepId){
        preparationStepService.deletePreparationStep(preparationStepId);
        logger.info("Deleted preparation step with ID: {}", preparationStepId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PostMapping
    @Operation(summary = "Add a new preparation step", description = "Adds a new preparation step and returns the added step details.")
    public ResponseEntity<PreparationStepDto> addPreparationStep(@RequestBody PreparationStepDto preparationStepDto){
        logger.info("Adding new preparation step: {}", preparationStepDto);
        preparationStepService.addPreparationStep(preparationStepDto);
        logger.info("Added new preparation step with details: {}", preparationStepDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(preparationStepDto);
    }

    @PutMapping("/{preparationStepId}")
    @Operation(summary = "Update a preparation step", description = "Updates a specific preparation step based on its ID and returns the updated step details.")
    public ResponseEntity<PreparationStepDto> updatePreparationStep(@PathVariable Integer preparationStepId, @RequestBody PreparationStepDto preparationStepDto) {
        logger.info("Attempting to update preparation step with ID: {}", preparationStepId);
        Optional<PreparationStepDto> updatedPreparationStep = preparationStepService.updatePreparationStep(preparationStepId, preparationStepDto);
        return updatedPreparationStep
                .map(step -> {
                    logger.info("Updated preparation step with ID: {}", preparationStepId);
                    return ResponseEntity.status(HttpStatus.OK).body(step);
                })
                .orElseGet(() -> {
                    logger.error("Failed to update preparation step with ID: {}", preparationStepId);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(preparationStepDto);
                });
    }
}
