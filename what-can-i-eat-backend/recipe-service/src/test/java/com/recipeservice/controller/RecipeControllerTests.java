package com.recipeservice.controller;

import com.recipeservice.dto.RecipeDto;
import com.recipeservice.service.RecipeService;
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
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@WebMvcTest(RecipeController.class)
public class RecipeControllerTests {
    @MockBean
    RecipeService recipeService;
    @Autowired
    WebApplicationContext webApplicationContext;
    @Autowired
    MockMvc mockMvc;

    @Test
    public void shouldCreateMockMvc(){
        assertNotNull(mockMvc);
    }

    @Test
    public void getRecipeByIdShouldReturn200WhenDataIsCorrect() throws Exception {
        // Tworzenie przykładowego DTO
        RecipeDto recipeDto = new RecipeDto(
                1,
                "name",
                "description",
                true,
                "source",
                10,
                10,
                10,
                200,
                "imageUrl",
                Collections.emptyList(),
                Collections.emptyList()
        );
        when(recipeService.getRecipeById(Mockito.anyInt())).thenReturn(Optional.of(recipeDto));
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/api/v1/recipes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Dodatkowe sprawdzenie ciała odpowiedzi przy użyciu AssertJ
        String responseBody = mvcResult.getResponse().getContentAsString();
        assertThat(responseBody).contains("\"name\":\"name\"");
    }
}
