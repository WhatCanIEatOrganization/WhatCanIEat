package com.example.ingredientservice.controller;

import com.example.ingredientservice.dto.RecipeIngredientDto;
import com.example.ingredientservice.service.RecipeIngredientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@WebMvcTest(RecipeIngredientController.class)
class RecipeIngredientControllerTest {
    @MockBean
    RecipeIngredientService recipeIngredientService;
    @Autowired
    WebApplicationContext webApplicationContext;
    @Autowired
    MockMvc mockMvc;

    RecipeIngredientDto recipeIngredientDto;
    RecipeIngredientDto secondRecipeIngredientDto;

    @BeforeEach
    public void setUp(){
        recipeIngredientDto = new RecipeIngredientDto(1, "test", "test", "test.url", "2 kg");
        secondRecipeIngredientDto = new RecipeIngredientDto(2, "test2", "test2", "test.url", "2 kg");
    }

    @Test
    void addNewRecipeIngredientShouldReturn201WhenDataIsCorrect() throws Exception {
        when(recipeIngredientService.addNewIngredient(recipeIngredientDto)).thenReturn(recipeIngredientDto);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(recipeIngredientDto);
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/v2/recipe-ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("test"));
    }




    @Test
    void getRecipeIngredientByIdShouldReturn200WhenDataIsCorrect() throws Exception {
        when(recipeIngredientService.getIngredientById(Mockito.anyInt())).thenReturn(Optional.of(recipeIngredientDto));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v2/recipe-ingredients/1")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        assertThat(responseBody).contains("\"id\":1");
    }

    @Test
    void addRecipeIngredients() {
    }

    @Test
    void getRecipeIngredientsByIdsShouldReturn200WhenDataIsCorrect() throws Exception {
        when(recipeIngredientService.findIngredientsById(List.of(1,2))).thenReturn(List.of(recipeIngredientDto, secondRecipeIngredientDto));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v2/recipe-ingredients?ids=1&ids=2")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        assertThat(responseBody).contains("\"name\":\"test\"");
        assertThat(responseBody).contains("\"name\":\"test2\"");
    }

    @Test
    void deleteRecipeIngredient() {
    }
}