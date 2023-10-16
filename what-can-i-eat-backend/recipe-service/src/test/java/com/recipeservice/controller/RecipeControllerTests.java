package com.recipeservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipeservice.dto.CreateRecipeDto;
import com.recipeservice.dto.IngredientDto;
import com.recipeservice.dto.RecipeDto;
import com.recipeservice.service.RecipeService;
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
import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;

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

    RecipeDto sampleRecipeDto;
    RecipeDto secondSampleRecipeDto;


    @BeforeEach
    public void setUp() {
        sampleRecipeDto = new RecipeDto(
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
        secondSampleRecipeDto = new RecipeDto(
                2,
                "name2",
                "description2",
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
    }

    @Test
    public void shouldCreateMockMvc() {
        assertNotNull(mockMvc);
    }

    @Test
    public void getRecipeByIdShouldReturn200WhenDataIsCorrect() throws Exception {
        when(recipeService.getRecipeById(Mockito.anyInt())).thenReturn(Optional.of(sampleRecipeDto));
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/api/v1/recipes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        assertThat(responseBody).contains("\"name\":\"name\"");
    }

    @Test
    public void getRecipeByIdShouldReturn404WhenDataIsIncorrect() throws Exception {
        when(recipeService.getRecipeById(Mockito.anyInt())).thenReturn(Optional.empty());

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/api/v1/recipes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
    }


    @Test
    public void addNewRecipeShouldReturn201WhenDataIsCorrect() throws Exception {
        CreateRecipeDto createRecipeDto = new CreateRecipeDto(
                sampleRecipeDto.id(),
                sampleRecipeDto.name(),
                sampleRecipeDto.description(),
                sampleRecipeDto.favorite(),
                sampleRecipeDto.source(),
                sampleRecipeDto.preptime(),
                sampleRecipeDto.waittime(),
                sampleRecipeDto.cooktime(),
                sampleRecipeDto.calories(),
                sampleRecipeDto.imageUrl(),
                sampleRecipeDto.preparationSteps(),
                new ArrayList<>()
        );
        when(recipeService.addNewRecipe(Mockito.any(CreateRecipeDto.class))).thenReturn(sampleRecipeDto);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(createRecipeDto);
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/v1/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("name"));

    }


    @Test
    public void getRecipesListShouldReturn200WithRecipeList() throws Exception {
        when(recipeService.getRecipesList()).thenReturn(Arrays.asList(sampleRecipeDto, secondSampleRecipeDto));

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/api/v1/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("name2"));
    }


    @Test
    public void deleteRecipeShouldReturn204WhenRecipeIdIsValid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/recipes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void getRandomRecipeShouldReturn200WithRecipe() throws Exception {
        when(recipeService.getRandomRecipe()).thenReturn(sampleRecipeDto);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/recipes/rng")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("name"));
    }

    @Test
    public void getIngredientsByIds_ShouldReturnListOfIngredients() throws Exception {
        // Initialize ingredients
        IngredientDto ingredient1 = new IngredientDto(1, "Onion", "test", "test", "test");
        IngredientDto ingredient2 = new IngredientDto(2, "Tomato", "test", "test", "test");
        List<IngredientDto> expectedIngredients = List.of(ingredient1, ingredient2);

        // Define behavior
        when(recipeService.getIngredientsByIds(List.of(1, 2))).thenReturn(expectedIngredients);

        // Perform request and validate response
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/recipes/ingredients")
                        .param("ids", "1,2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Onion"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Tomato"));
    }

    @Test
    public void searchRecipesByIngredients_ShouldReturnListOfRecipes() throws Exception {
        List<RecipeDto> expectedRecipes = List.of(sampleRecipeDto, secondSampleRecipeDto);
        when(recipeService.getRecipesByIngredients(List.of("name", "name2"))).thenReturn(expectedRecipes);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/recipes/search")
                        .param("ingredients", "name,name2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("name2"));
    }
}
