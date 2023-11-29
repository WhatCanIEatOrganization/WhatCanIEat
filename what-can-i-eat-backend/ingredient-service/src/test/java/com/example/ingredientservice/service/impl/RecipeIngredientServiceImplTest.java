package com.example.ingredientservice.service.impl;

import com.example.ingredientservice.dto.RecipeIngredientDto;
import com.example.ingredientservice.mapper.RecipeIngredientMapper;
import com.example.ingredientservice.model.RecipeIngredient;
import com.example.ingredientservice.repository.RecipeIngredientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RecipeIngredientServiceImplTest {

    @Autowired
    private RecipeIngredientServiceImpl ingredientService;

    @MockBean
    private RecipeIngredientRepository ingredientRepository;

    private RecipeIngredient sampleIngredient;
    private RecipeIngredient secondSampleIngredient;

    @BeforeEach
    public void setUp() {
        sampleIngredient = new RecipeIngredient();
        sampleIngredient.setId(1);
        sampleIngredient.setName("Bananas");
        sampleIngredient.setDescription("test");
        sampleIngredient.setAmountWithUnit("200 g");

        secondSampleIngredient = new RecipeIngredient();
        secondSampleIngredient.setId(2);
        secondSampleIngredient.setName("Almonds");
        secondSampleIngredient.setDescription("test");
        secondSampleIngredient.setAmountWithUnit("150 g");
    }

    @Test
    void addNewIngredientShouldSaveIngredientToDatabase() {
        RecipeIngredientDto ingredientDto = RecipeIngredientMapper.mapToDto(sampleIngredient);
        RecipeIngredientDto result = ingredientService.addNewIngredient(ingredientDto);
        assertEquals(ingredientDto, result);
    }

    @Test
    void addNewIngredientsShouldSaveIngredientsToDatabase() {
        RecipeIngredientDto sampleIngredientDto = RecipeIngredientMapper.mapToDto(sampleIngredient);
        RecipeIngredientDto secondSampleIngredientDto = RecipeIngredientMapper.mapToDto(secondSampleIngredient);
        List<RecipeIngredientDto> mockDtoList = Arrays.asList(sampleIngredientDto, secondSampleIngredientDto);
        List<RecipeIngredientDto> result = ingredientService.addNewIngredients(mockDtoList);
        assertEquals(mockDtoList.size(), result.size());;
    }

    @Test
    void deleteIngredient() {
        int ingredientId = 1;
        ingredientService.deleteIngredient(ingredientId);
        verify(ingredientRepository, times(1)).deleteById(ingredientId);

    }

    @Test
    void getIngredientById() {
    }

    @Test
    void findIngredientsById() {
    }
}