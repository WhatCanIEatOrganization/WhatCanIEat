package com.example.ingredientservice.service.impl;

import com.example.ingredientservice.dto.BasicIngredientDto;
import com.example.ingredientservice.model.BasicIngredient;
import com.example.ingredientservice.repository.BasicIngredientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BasicIngredientServiceImplTest {

    @Autowired
    private BasicIngredientServiceImpl ingredientService;

    @MockBean
    private BasicIngredientRepository ingredientRepository;

    BasicIngredient sampleIngredient;
    BasicIngredient secondSampleIngredient;

    @BeforeEach
    public void setUp() {
        sampleIngredient = new BasicIngredient();
        sampleIngredient.setId(1);
        sampleIngredient.setName("Lemon");
        sampleIngredient.setLegacyId(1);
        sampleIngredient.setImageUrl("test.com");
        sampleIngredient.setDescription("test");

        secondSampleIngredient = new BasicIngredient();
        secondSampleIngredient.setId(2);
        secondSampleIngredient.setName("Pepper");
        secondSampleIngredient.setLegacyId(2);
        secondSampleIngredient.setImageUrl("test2.com");
        secondSampleIngredient.setDescription("test2");
    }

    @Test
    void getBasicIngredientByIdShouldReturnIngredient() {
        when(ingredientRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(sampleIngredient));
        Optional<BasicIngredientDto> result = ingredientService.getBasicIngredientById(sampleIngredient.getId());
        assertTrue(result.isPresent());
        assertEquals(sampleIngredient.getName(), result.get().name());
    }

    @Test
    void getBasicIngredientByIdShouldReturnEmptyOptional() {
        when(ingredientRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        Optional<BasicIngredientDto> result = ingredientService.getBasicIngredientById(sampleIngredient.getId());
        assertTrue(result.isEmpty());
    }

    @Test
    void getBasicIngredientByNameShouldReturnIngredient() {
        when(ingredientRepository.findBasicIngredientByName(Mockito.anyString())).thenReturn(Optional.of(sampleIngredient));
        Optional<BasicIngredientDto> result = ingredientService.getBasicIngredientByName(sampleIngredient.getName());
        assertTrue(result.isPresent());
        assertEquals(sampleIngredient.getId(), result.get().id());
    }


    @Test
    void getBasicIngredientByNameShouldReturnEmptyOptional() {
        when(ingredientRepository.findBasicIngredientByName(Mockito.anyString())).thenReturn(Optional.empty());
        Optional<BasicIngredientDto> result = ingredientService.getBasicIngredientByName(sampleIngredient.getName());
        assertTrue(result.isEmpty());
    }

    @Test
    void getAllBasicIngredientsShouldReturnEmptyList() {
        List<BasicIngredient> emptyMockList = List.of();
        when(ingredientRepository.findAll()).thenReturn(emptyMockList);
        List<BasicIngredientDto> result = ingredientService.getAllBasicIngredients();
        assertTrue(result.isEmpty());
    }

    @Test
    void getAllBasicIngredientsShouldReturnListOfIngredients() {
        List<BasicIngredient> mockIngredients = Arrays.asList(sampleIngredient, secondSampleIngredient);
        when(ingredientRepository.findAll()).thenReturn(mockIngredients);
        List<BasicIngredientDto> result = ingredientService.getAllBasicIngredients();
        assertEquals(mockIngredients.size(), result.size());
    }
}