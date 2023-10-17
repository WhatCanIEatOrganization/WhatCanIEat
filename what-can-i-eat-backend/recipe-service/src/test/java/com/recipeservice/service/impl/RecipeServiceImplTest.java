package com.recipeservice.service.impl;


import com.recipeservice.dto.CreateRecipeDto;
import com.recipeservice.dto.IngredientDto;
import com.recipeservice.dto.RecipeDto;
import com.recipeservice.mapper.RecipeMapper;
import com.recipeservice.model.Recipe;
import com.recipeservice.repository.RecipeRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;

import com.recipeservice.service.PexelsService;
import com.recipeservice.service.RecipeTagService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@SpringBootTest
public class RecipeServiceImplTest {

    @Autowired
    private RecipeServiceImpl recipeService;

    @MockBean
    RecipeRepository recipeRepository;

    @MockBean
    PexelsService pexelsService;
    @Mock
    private RecipeTagService recipeTagService;

    @Mock
    private WebClient mockWebClient;


    RecipeDto sampleRecipeDto;
    RecipeDto secondSampleRecipeDto;
    CreateRecipeDto createRecipeDto;
    List<IngredientDto> ingredientDtos;

    @BeforeEach
    public void setUp() {
        sampleRecipeDto = new RecipeDto(
                0,
                "name",
                "description",
                true,
                null,
                10,
                10,
                10,
                200,
                "imageUrl",
                Collections.emptyList(),
                Collections.emptyList()
        );
        secondSampleRecipeDto = new RecipeDto(
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
        createRecipeDto = new CreateRecipeDto(
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
        ingredientDtos = Arrays.asList(new IngredientDto(1, "test1", "test1", "test1", "test1"), new IngredientDto(2,"test2", "test2", "test2", "test2"));
        ReflectionTestUtils.setField(recipeService, "webClient", mockWebClient);
    }
    @Test
    void addNewRecipe() {
        Recipe recipe = RecipeMapper.createRecipeDtoToEntity(createRecipeDto);
        WebClient.RequestBodySpec mockRequestBodySpec = mock(WebClient.RequestBodySpec.class);
        WebClient.RequestHeadersSpec mockRequestHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec mockResponseSpec = mock(WebClient.ResponseSpec.class);
        WebClient.RequestBodyUriSpec mockRequestBodyUriSpec = mock(WebClient.RequestBodyUriSpec.class);

        when(mockWebClient.post()).thenReturn(mockRequestBodyUriSpec);
        when(mockRequestBodyUriSpec.uri("/api/v2/recipe-ingredients/batch")).thenReturn(mockRequestBodySpec);
        when(mockRequestBodySpec.contentType(MediaType.APPLICATION_JSON)).thenReturn(mockRequestBodySpec);
        when(mockRequestBodySpec.body(any())).thenReturn(mockRequestHeadersSpec);
        when(mockRequestHeadersSpec.retrieve()).thenReturn(mockResponseSpec);
        when(mockResponseSpec.bodyToMono(new ParameterizedTypeReference<List<IngredientDto>>() {})).thenReturn(Mono.just(ingredientDtos));
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);
        RecipeDto result = recipeService.addNewRecipe(createRecipeDto);
        assertEquals(sampleRecipeDto, result);
    }



    @Test
    void getRecipesByIngredients() {
    }

    @Test
    void getRecipesList() {
    }

    @Test
    void getFavoriteRecipes() {
    }

    @Test
    void deleteRecipe() {
    }

    @Test
    void getRandomRecipe() {
    }

    @Test
    void getRecipeById() {
    }

    @Test
    void getIngredientsByIds() {
    }

    @Test
    void updateRecipeImages() {
    }
}