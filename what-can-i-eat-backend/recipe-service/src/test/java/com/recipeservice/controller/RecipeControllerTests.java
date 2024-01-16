package com.recipeservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipeservice.dto.CreateRecipeDto;
import com.recipeservice.dto.IngredientDto;
import com.recipeservice.dto.RecipeDto;
import com.recipeservice.service.RecipeService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(RecipeController.class)
@AutoConfigureMockMvc(addFilters = false)
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
        when(recipeService.addNewRecipe(any(CreateRecipeDto.class))).thenReturn(sampleRecipeDto);

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
    public void deleteRecipeShouldReturn204WhenRecipeIdIsValid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/recipes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void getRandomRecipeShouldReturn200WithRecipe() throws Exception {
        when(recipeService.getDailyRecipe()).thenReturn(sampleRecipeDto);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/recipes/rng")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("name"));
    }

    @Test
    public void getIngredientsByIds_ShouldReturnListOfIngredients() throws Exception {
        IngredientDto ingredient1 = new IngredientDto(1, "Onion", "test", "test", "test");
        IngredientDto ingredient2 = new IngredientDto(2, "Tomato", "test", "test", "test");
        List<IngredientDto> expectedIngredients = List.of(ingredient1, ingredient2);
        when(recipeService.getIngredientsByIds(List.of(1, 2))).thenReturn(expectedIngredients);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/recipes/ingredients")
                        .param("ids", "1,2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(ingredient1.id()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(ingredient1.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(ingredient2.id()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value(ingredient2.name()));
    }

    @Test
    public void searchRecipesByIngredients_ShouldReturnListOfRecipes() throws Exception {
        List<RecipeDto> expectedRecipes = List.of(sampleRecipeDto, secondSampleRecipeDto);
        when(recipeService.searchRecipesByTags(List.of("name", "name2"))).thenReturn(expectedRecipes);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/recipes/search")
                        .param("ingredients", "name,name2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("name2"));
    }


    @Test
    public void getSortedRecipesWithPaginationShouldReturnRecipes() throws Exception {
        int page = 0;
        int size = 10;
        String sortBy = "name";
        List<RecipeDto> expectedRecipes = Arrays.asList(sampleRecipeDto, secondSampleRecipeDto);

        when(recipeService.findAllRecipes(any(Pageable.class))).thenReturn(expectedRecipes);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/recipes")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .param("sortBy", sortBy)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(sampleRecipeDto.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value(secondSampleRecipeDto.name()));
    }

    @Test
    public void getSortedRecipesWithPaginationShouldReturnEmptyListWhenNoRecipes() throws Exception {
        when(recipeService.findAllRecipes(any(Pageable.class))).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/recipes")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "name")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());
    }

    @Test
    public void getSortedRecipesWithPaginationShouldHandlePaginationCorrectly() throws Exception {
        int page = 1;
        int size = 5;
        when(recipeService.findAllRecipes(any(Pageable.class))).thenReturn(Arrays.asList(sampleRecipeDto));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/recipes")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .param("sortBy", "name")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", Matchers.is(1)));
    }

    @Test
    public void getSortedRecipesWithPaginationShouldHandleSortingCorrectly() throws Exception {
        String sortBy = "calories";
        when(recipeService.findAllRecipes(any(Pageable.class))).thenReturn(Arrays.asList(sampleRecipeDto, secondSampleRecipeDto));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/recipes")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", sortBy)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].calories").value(sampleRecipeDto.calories()));
    }

    @Test
    public void getSortedRecipesWithInvalidParametersShouldReturnBadRequest() throws Exception {
        int invalidPage = -1;
        int size = 10;
        String sortBy = "name";
        Mockito.when(recipeService.findAllRecipes(any(Pageable.class)))
                .thenThrow(new IllegalArgumentException("Page index must not be less than zero"));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/recipes")
                        .param("page", String.valueOf(invalidPage))
                        .param("size", String.valueOf(size))
                        .param("sortBy", sortBy)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    public void searchRecipesByFridgeIngredientsShouldReturnRecipes() throws Exception {
        List<RecipeDto> expectedRecipes = Arrays.asList(sampleRecipeDto, secondSampleRecipeDto);
        when(recipeService.searchRecipesByFridgeIngredients()).thenReturn(expectedRecipes);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/recipes/search/fridge-ingredients")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(sampleRecipeDto.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value(secondSampleRecipeDto.name()));
    }

    @Test
    public void searchRecipesByFridgeIngredientsShouldReturnEmptyListWhenNoRecipes() throws Exception {
        when(recipeService.searchRecipesByFridgeIngredients()).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/recipes/search/fridge-ingredients")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());
    }
    @Test
    public void searchRecipesByFridgeIngredientsShouldHandleExceptions() throws Exception {
        when(recipeService.searchRecipesByFridgeIngredients()).thenThrow(new RuntimeException("Error fetching data"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/recipes/search/fridge-ingredients")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }



}
