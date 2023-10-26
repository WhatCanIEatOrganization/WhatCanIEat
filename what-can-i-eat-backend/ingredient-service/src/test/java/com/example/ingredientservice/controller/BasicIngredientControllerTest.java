package com.example.ingredientservice.controller;

import com.example.ingredientservice.dto.BasicIngredientDto;
import com.example.ingredientservice.service.BasicIngredientService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@WebMvcTest(BasicIngredientController.class)
class BasicIngredientControllerTest {

    @MockBean
    BasicIngredientService basicIngredientService;
    @Autowired
    WebApplicationContext webApplicationContext;
    @Autowired
    MockMvc mockMvc;

    BasicIngredientDto basicIngredientDto;
    BasicIngredientDto secondBasicIngredientDto;

    @BeforeEach
    public void setUp(){
        basicIngredientDto = new BasicIngredientDto(1, "test", "test", 1, "test.com");
        secondBasicIngredientDto = new BasicIngredientDto(2, "test2", "test2", 2, "test2.com");
    }


    @Test
    void getRecipeIngredientById() throws Exception {
        when(basicIngredientService.getBasicIngredientById(Mockito.anyInt())).thenReturn(Optional.of(basicIngredientDto));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v2/basic-ingredients/1")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        assertThat(responseBody).contains("\"id\":1");
    }

    @Test
    void getBasicIngredientByName() throws Exception {
        when(basicIngredientService.getBasicIngredientByName(Mockito.anyString())).thenReturn(Optional.of(basicIngredientDto));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v2/basic-ingredients/name/test")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        assertThat(responseBody).contains("\"name\":\"test\"");
    }

    @Test
    void getBasicIngredients() {
    }
}