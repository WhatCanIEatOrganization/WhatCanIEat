package com.recipeservice.service.impl;


import com.recipeservice.dto.CreateRecipeDto;
import com.recipeservice.dto.IngredientDto;
import com.recipeservice.dto.RecipeDto;
import com.recipeservice.mapper.RecipeMapper;
import com.recipeservice.mapper.RecipeTagMapper;
import com.recipeservice.model.Recipe;
import com.recipeservice.repository.RecipeRepository;

import static org.junit.jupiter.api.Assertions.*;
import com.recipeservice.service.PexelsService;
import com.recipeservice.service.RecipeTagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import static org.mockito.Mockito.*;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@SpringBootTest
public class RecipeServiceImplTest {

    @Autowired
    private RecipeServiceImpl recipeService;

    @MockBean
    private RecipeRepository recipeRepository;

    @MockBean
    private PexelsService pexelsService;
    @MockBean
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
                List.of(1,2)
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
        when(recipeTagService.generateRecipeTags(any(CreateRecipeDto.class), any(Integer.class))).thenReturn(recipe.getTags().stream().map(RecipeTagMapper::toDto).collect(Collectors.toSet()));
        RecipeDto result = recipeService.addNewRecipe(createRecipeDto);
        assertEquals(sampleRecipeDto, result);
    }

    @Test
    void getIngredientsByIds_ReturnsListOfIngredients_WhenCalledWithValidIds() {
        List<Integer> ids = List.of(1, 2, 3);
        List<IngredientDto> expectedIngredients = List.of(
                new IngredientDto(1, "Salt", "test","test","test"),
                new IngredientDto(2, "Pepper","test","test","test"),
                new IngredientDto(3, "Sugar","test","test","test")
        );
        WebClient.RequestHeadersUriSpec mockRequestHeadersUriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec mockRequestHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec mockResponseSpec = mock(WebClient.ResponseSpec.class);
        when(mockWebClient.get()).thenReturn(mockRequestHeadersUriSpec);
        when(mockRequestHeadersUriSpec.uri(any(Function.class))).thenReturn(mockRequestHeadersSpec);
        when(mockRequestHeadersSpec.retrieve()).thenReturn(mockResponseSpec);
        when(mockResponseSpec.bodyToMono(new ParameterizedTypeReference<List<IngredientDto>>() {})).thenReturn(Mono.just(expectedIngredients));
        List<IngredientDto> result = recipeService.getIngredientsByIds(ids);
        assertEquals(expectedIngredients, result);
    }



    @Test
    void getRecipesByIngredients() {
        int count = 2;
        List<String> tags = List.of("ingredient1", "ingredient2");
        List<Integer> ids = List.of(sampleRecipeDto.id(), secondSampleRecipeDto.id());
        List<Recipe> recipes = List.of(RecipeMapper.toEntity(sampleRecipeDto), RecipeMapper.toEntity(secondSampleRecipeDto));
        when(recipeRepository.findRecipesIdsByTags(tags, count)).thenReturn(ids);
        when(recipeRepository.findAllById(ids)).thenReturn(recipes);
        List<RecipeDto> foundRecipes = recipeService.searchRecipesByTags(tags);
        assertEquals(ids.size(), foundRecipes.size());
        assertTrue(foundRecipes.contains(sampleRecipeDto));
        assertTrue(foundRecipes.contains(secondSampleRecipeDto));
    }

    @Test
    void searchRecipesByTagsShouldReturnEmptyList() {
        List<String> tags = List.of("nonExistentTag1", "nonExistentTag2");
        when(recipeRepository.findRecipesIdsByTags(tags, tags.size())).thenReturn(Collections.emptyList());
        List<RecipeDto> foundRecipes = recipeService.searchRecipesByTags(tags);
        assertTrue(foundRecipes.isEmpty());
    }

    @Test
    void searchRecipesByTagsShouldReturnSingleResult() {
        List<String> tags = List.of("singleTag");
        List<Integer> ids = List.of(sampleRecipeDto.id());
        List<Recipe> recipes = List.of(RecipeMapper.toEntity(sampleRecipeDto));
        when(recipeRepository.findRecipesIdsByTags(tags, 1)).thenReturn(ids);
        when(recipeRepository.findAllById(ids)).thenReturn(recipes);
        List<RecipeDto> foundRecipes = recipeService.searchRecipesByTags(tags);
        assertEquals(1, foundRecipes.size());
        assertTrue(foundRecipes.contains(sampleRecipeDto));
    }

    @Test
    void searchRecipesByTagsShouldReturnRecipeWithPartialIngredients() {
        List<String> tags = List.of("tag1", "tag2", "tag3");
        List<Integer> ids = List.of(sampleRecipeDto.id());
        List<Recipe> recipes = List.of(RecipeMapper.toEntity(sampleRecipeDto));
        when(recipeRepository.findRecipesIdsByTags(tags, 3)).thenReturn(ids);
        when(recipeRepository.findAllById(ids)).thenReturn(recipes);
        List<RecipeDto> foundRecipes = recipeService.searchRecipesByTags(tags);
        assertEquals(1, foundRecipes.size());
        assertTrue(foundRecipes.contains(sampleRecipeDto));
    }

    @Test
    void searchRecipesByTagsShouldThrowRepositoryError() {
        List<String> tags = List.of("tag1");
        when(recipeRepository.findRecipesIdsByTags(tags, 1)).thenThrow(new RuntimeException("DB error"));
        assertThrows(RuntimeException.class, () -> recipeService.searchRecipesByTags(tags));
    }

//    @Test
//    void getRecipesListShouldReturnListOfRecipes() {
//        List<Recipe> recipes = List.of(RecipeMapper.toEntity(sampleRecipeDto), RecipeMapper.toEntity(secondSampleRecipeDto));
//        when(recipeRepository.findAll()).thenReturn(recipes);
//        List<RecipeDto> recipesList = recipeService.getRecipesList();
//        assertEquals(2, recipesList.size());
//        assertTrue(recipesList.containsAll(List.of(sampleRecipeDto, secondSampleRecipeDto)));
//    }
//
//    @Test
//    void getRecipesListShouldReturnEmptyList() {
//        when(recipeRepository.findAll()).thenReturn(Collections.emptyList());
//        List<RecipeDto> recipesList = recipeService.getRecipesList();
//        assertTrue(recipesList.isEmpty());
//    }
//
//    @Test
//    void getRecipesListShouldReturnSingleRecipe() {
//        List<Recipe> recipes = List.of(RecipeMapper.toEntity(sampleRecipeDto));
//        when(recipeRepository.findAll()).thenReturn(recipes);
//        List<RecipeDto> recipesList = recipeService.getRecipesList();
//        assertEquals(1, recipesList.size());
//        assertTrue(recipesList.contains(sampleRecipeDto));
//    }


    @Test
    void getFavoriteRecipes() {
    }


    @Test
    void deleteRecipeWithValidId() {
        int recipeId = 1;
        doNothing().when(recipeRepository).deleteById(recipeId);
        recipeService.deleteRecipe(recipeId);
        verify(recipeRepository, times(1)).deleteById(recipeId);
    }

    @Test
    void deleteRecipeShouldThrowExceptionWhenNonExistentId() {
        int nonExistentRecipeId = 999;
        doThrow(new EmptyResultDataAccessException(1)).when(recipeRepository).deleteById(nonExistentRecipeId);

        assertThrows(EmptyResultDataAccessException.class, () -> recipeService.deleteRecipe(nonExistentRecipeId));
        verify(recipeRepository, times(1)).deleteById(nonExistentRecipeId);
    }

    @Test
    void getDailyRecipeShouldReturnRecipe() {
        List<Recipe> recipesList = List.of(RecipeMapper.toEntity(sampleRecipeDto), RecipeMapper.toEntity(secondSampleRecipeDto));
        when(recipeRepository.findAll()).thenReturn(recipesList);
        RecipeDto result = recipeService.getDailyRecipe();
        assertNotNull(result);
    }

    @Test
    void getRecipeByIdShouldReturnsRecipe() {
        int givenId = 1;
        Recipe sampleRecipe = RecipeMapper.toEntity(sampleRecipeDto);
        when(recipeRepository.findByIdWithRelations(any(Integer.class))).thenReturn(Optional.of(sampleRecipe));
        Optional<RecipeDto> result = recipeService.getRecipeById(givenId);
        assertTrue(result.isPresent());
        assertEquals(sampleRecipeDto, result.get());
    }

    @Test
    void getRecipeByIdShouldReturnsEmptyOptional() {
        int givenId = 2;
        when(recipeRepository.findById(givenId)).thenReturn(Optional.empty());
        Optional<RecipeDto> result = recipeService.getRecipeById(givenId);
        assertFalse(result.isPresent());
    }

    @Test
    void updateRecipeImages() {
    }
}