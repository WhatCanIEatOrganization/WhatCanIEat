package com.recipeservice.service.impl;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.recipeservice.config.RedisTestConfiguration;
import com.recipeservice.dto.CreateRecipeDto;
import com.recipeservice.dto.IngredientDto;
import com.recipeservice.dto.RecipeDto;
import com.recipeservice.mapper.RecipeMapper;
import com.recipeservice.mapper.RecipeTagMapper;
import com.recipeservice.model.Recipe;
import com.recipeservice.repository.RecipeRepository;

import static org.junit.jupiter.api.Assertions.*;

import com.recipeservice.service.RecipeTagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import static org.mockito.Mockito.*;

import java.net.URI;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Import(RedisTestConfiguration.class)
public class RecipeServiceImplTest {


    @Autowired
    private RecipeServiceImpl recipeService;

    @MockBean
    private RecipeRepository recipeRepository;
    @MockBean
    private RecipeTagService recipeTagService;

    @Mock
    private WebClient mockWebClient;

    WebClient.RequestHeadersUriSpec mockRequestHeadersUriSpec = mock(WebClient.RequestHeadersUriSpec.class);
    WebClient.RequestHeadersSpec mockRequestHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
    WebClient.ResponseSpec mockResponseSpec = mock(WebClient.ResponseSpec.class);


    @Captor
    private ArgumentCaptor<Function<UriBuilder, URI>> uriCaptor;

    RecipeDto sampleRecipeDto;
    RecipeDto secondSampleRecipeDto;
    CreateRecipeDto createRecipeDto;
    List<IngredientDto> ingredientDtos;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
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
        when(mockWebClient.get()).thenReturn(mockRequestHeadersUriSpec);
        when(mockRequestHeadersUriSpec.uri(any(Function.class))).thenReturn(mockRequestHeadersSpec);
        when(mockRequestHeadersSpec.retrieve()).thenReturn(mockResponseSpec);
        when(mockResponseSpec.bodyToMono(new ParameterizedTypeReference<List<IngredientDto>>() {})).thenReturn(Mono.just(expectedIngredients));
        List<IngredientDto> result = recipeService.getIngredientsByIds(ids);
        assertEquals(expectedIngredients, result);
    }



    @Test
    void getIngredientsByIdsShouldUseCorrectUri() {
        List<Integer> ids = List.of(1, 2, 3);
        List<IngredientDto> expectedIngredients = List.of(
                new IngredientDto(1, "Salt", "test", "test", "test"),
                new IngredientDto(2, "Pepper", "test", "test", "test"),
                new IngredientDto(3, "Sugar", "test", "test", "test")
        );

        when(mockWebClient.get()).thenReturn(mockRequestHeadersUriSpec);
        when(mockRequestHeadersUriSpec.uri(any(Function.class))).thenReturn(mockRequestHeadersSpec);
        when(mockRequestHeadersSpec.retrieve()).thenReturn(mockResponseSpec);
        when(mockResponseSpec.bodyToMono(any(ParameterizedTypeReference.class)))
                .thenReturn(Mono.just(expectedIngredients));
        recipeService.getIngredientsByIds(ids);
        verify(mockRequestHeadersUriSpec).uri(uriCaptor.capture());
        Function<UriBuilder, URI> uriFunction = uriCaptor.getValue();
        UriBuilder builder = new DefaultUriBuilderFactory().builder();
        URI actualUri = uriFunction.apply(builder);

        assertEquals("/api/v2/recipe-ingredients", actualUri.getPath());
        assertEquals("ids=1,2,3", actualUri.getQuery());

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

    @Test
    void findAllRecipesReturnsListOfRecipeDto() {
        Recipe recipe1 = RecipeMapper.toEntity(sampleRecipeDto);
        Recipe recipe2 = RecipeMapper.toEntity(secondSampleRecipeDto);
        Page<Recipe> page = new PageImpl<>(List.of(recipe1, recipe2));
        Pageable pageable = PageRequest.of(0, 10, Sort.unsorted());
        when(recipeRepository.findAll(pageable)).thenReturn(page);
        List<RecipeDto> result = recipeService.findAllRecipes(pageable);
        assertEquals(2, result.size());
    }

    @Test
    void findAllRecipesReturnsListOfRecipeDtoWithTenRecipes() {
        List<Recipe> recipeList = IntStream.range(0, 10)
                .mapToObj(i -> RecipeMapper.toEntity(new RecipeDto(
                        i, "Name" + i, "Description" + i, false,
                        "Source" + i, 10, 10, 10, 200,
                        "imageUrl" + i, Collections.emptyList(),
                        Collections.emptyList())))
                .collect(Collectors.toList());
        Page<Recipe> page = new PageImpl<>(recipeList);
        Pageable pageable = PageRequest.of(0, 10, Sort.unsorted());
        when(recipeRepository.findAll(pageable)).thenReturn(page);
        List<RecipeDto> result = recipeService.findAllRecipes(pageable);
        assertEquals(10, result.size());
    }

    @Test
    void findAllRecipesReturnsEmptyListWhenNoRecipesPresent() {
        Page<Recipe> emptyPage = new PageImpl<>(Collections.emptyList());
        Pageable pageable = PageRequest.of(0, 10, Sort.unsorted());

        when(recipeRepository.findAll(pageable)).thenReturn(emptyPage);

        List<RecipeDto> result = recipeService.findAllRecipes(pageable);

        assertTrue(result.isEmpty());
    }

    @Test
    void findAllRecipesReturnsSingleRecipeDtoWhenOneRecipePresent() {
        Recipe singleRecipe = RecipeMapper.toEntity(sampleRecipeDto);
        Page<Recipe> singleRecipePage = new PageImpl<>(Collections.singletonList(singleRecipe));
        Pageable pageable = PageRequest.of(0, 10, Sort.unsorted());

        when(recipeRepository.findAll(pageable)).thenReturn(singleRecipePage);

        List<RecipeDto> result = recipeService.findAllRecipes(pageable);

        assertEquals(1, result.size());
    }

    @Test
    void findAllRecipesHandlesLargeNumberOfRecipes() {
        List<Recipe> largeListOfRecipes = IntStream.range(0, 100)
                .mapToObj(i -> RecipeMapper.toEntity(new RecipeDto(
                        i, "Name" + i, "Description" + i, false,
                        "Source" + i, 10, 10, 10, 200,
                        "imageUrl" + i, Collections.emptyList(),
                        Collections.emptyList())))
                .collect(Collectors.toList());
        Page<Recipe> largeRecipePage = new PageImpl<>(largeListOfRecipes);
        Pageable pageable = PageRequest.of(0, 100, Sort.unsorted());

        when(recipeRepository.findAll(pageable)).thenReturn(largeRecipePage);

        List<RecipeDto> result = recipeService.findAllRecipes(pageable);

        assertEquals(100, result.size());
    }

    @Test
    void findAllRecipesWithDifferentPageableParameters() {
        List<Recipe> recipes = IntStream.range(0, 20)
                .mapToObj(i -> RecipeMapper.toEntity(new RecipeDto(
                        i, "Name" + i, "Description" + i, false,
                        "Source" + i, 10, 10, 10, 200,
                        "imageUrl" + i, Collections.emptyList(),
                        Collections.emptyList())))
                .collect(Collectors.toList());
        Page<Recipe> recipePage = new PageImpl<>(recipes.subList(10, 20));
        Pageable pageable = PageRequest.of(1, 10, Sort.by("name").descending());
        when(recipeRepository.findAll(pageable)).thenReturn(recipePage);
        List<RecipeDto> result = recipeService.findAllRecipes(pageable);
        assertEquals(10, result.size());
    }

    @Test
    void findAllRecipesReturnsCorrectRecipesOnGivenPage() {
        int totalRecipes = 40;
        int pageSize = 10;
        int pageNumber = 2;
        List<Recipe> allRecipes = IntStream.range(0, totalRecipes)
                .mapToObj(i -> RecipeMapper.toEntity(new RecipeDto(
                        i, "Name" + i, "Description" + i, false,
                        "Source" + i, 10, 10, 10, 200,
                        "imageUrl" + i, Collections.emptyList(),
                        Collections.emptyList())))
                .collect(Collectors.toList());
        int start = pageNumber * pageSize;
        List<Recipe> expectedRecipesOnPage = allRecipes.subList(start, Math.min(start + pageSize, totalRecipes));
        Page<Recipe> recipePage = new PageImpl<>(expectedRecipesOnPage, PageRequest.of(pageNumber, pageSize), totalRecipes);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.unsorted());
        when(recipeRepository.findAll(pageable)).thenReturn(recipePage);
        List<RecipeDto> result = recipeService.findAllRecipes(pageable);
        assertEquals(expectedRecipesOnPage.size(), result.size());
        IntStream.range(0, expectedRecipesOnPage.size()).forEach(i -> {
            Recipe expectedRecipe = expectedRecipesOnPage.get(i);
            RecipeDto resultDto = result.get(i);
            assertEquals(expectedRecipe.getId(), resultDto.id());
        });
    }
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
    void searchRecipesByFridgeIngredientsReturnsListOfRecipeDto() {
        List<String> fridgeIngredients = List.of("Onion", "Tomato");
        JsonNode onionNode = JsonNodeFactory.instance.objectNode().put("name", "Onion");
        JsonNode tomatoNode = JsonNodeFactory.instance.objectNode().put("name", "Tomato");
        when(mockWebClient.get()).thenReturn(mockRequestHeadersUriSpec);
        when(mockRequestHeadersUriSpec.uri("https://j9kvt6f27i.execute-api.eu-central-1.amazonaws.com/Stage/ingredients")).thenReturn(mockRequestHeadersSpec);
        when(mockRequestHeadersSpec.retrieve()).thenReturn(mockResponseSpec);
        when(mockResponseSpec.bodyToFlux(JsonNode.class)).thenReturn(Flux.just(onionNode, tomatoNode));
        List<Integer> recipeIds = List.of(1, 2);
        List<Recipe> recipes = List.of(RecipeMapper.toEntity(sampleRecipeDto),RecipeMapper.toEntity(secondSampleRecipeDto));
        when(recipeRepository.findRecipesByMatchingTags(fridgeIngredients)).thenReturn(recipeIds);
        when(recipeRepository.findAllById(recipeIds)).thenReturn(recipes);
        List<RecipeDto> result = recipeService.searchRecipesByFridgeIngredients();
        assertEquals(2, result.size());
    }

    @Test
    void searchRecipesByFridgeIngredientsReturnsEmptyListWhenNoIngredients() {
        when(mockWebClient.get()).thenReturn(mockRequestHeadersUriSpec);
        when(mockRequestHeadersUriSpec.uri("https://j9kvt6f27i.execute-api.eu-central-1.amazonaws.com/Stage/ingredients")).thenReturn(mockRequestHeadersSpec);
        when(mockRequestHeadersSpec.retrieve()).thenReturn(mockResponseSpec);
        when(mockResponseSpec.bodyToFlux(JsonNode.class)).thenReturn(Flux.empty()); // Brak składników
        when(recipeRepository.findRecipesByMatchingTags(anyList())).thenReturn(Collections.emptyList());

        List<RecipeDto> result = recipeService.searchRecipesByFridgeIngredients();
        assertTrue(result.isEmpty());
    }

    @Test
    void searchRecipesByFridgeIngredientsHandlesExternalServiceErrors() {
        when(mockWebClient.get()).thenReturn(mockRequestHeadersUriSpec);
        when(mockRequestHeadersUriSpec.uri("https://j9kvt6f27i.execute-api.eu-central-1.amazonaws.com/Stage/ingredients")).thenReturn(mockRequestHeadersSpec);
        when(mockRequestHeadersSpec.retrieve()).thenReturn(mockResponseSpec);
        when(mockResponseSpec.bodyToFlux(JsonNode.class)).thenReturn(Flux.error(new RuntimeException("External service error")));

        Exception exception = assertThrows(RuntimeException.class, () -> recipeService.searchRecipesByFridgeIngredients());
        assertEquals("External service error", exception.getMessage());
    }



    @Test
    void updateRecipeImages() {
    }
}