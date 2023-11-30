package com.recipeservice.service.impl;


import com.fasterxml.jackson.databind.JsonNode;
import com.recipeservice.dto.CreateRecipeDto;
import com.recipeservice.dto.IngredientDto;
import com.recipeservice.dto.RecipeDto;
import com.recipeservice.mapper.RecipeMapper;
import com.recipeservice.model.Recipe;
import com.recipeservice.repository.RecipeRepository;
import com.recipeservice.service.PexelsService;
import com.recipeservice.service.RecipeService;
import com.recipeservice.service.RecipeTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class RecipeServiceImpl implements RecipeService {



    private final RecipeRepository recipeRepository;
    private final PexelsService pexelsService;
    private final RecipeTagService recipeTagService;
    private final WebClient webClient;


    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository, PexelsService pexelsService, RecipeTagService recipeTagService, WebClient.Builder webClientBuilder,
                             @Value("${ingredient.service.url}") String ingredientServiceUrl) {
        this.recipeRepository = recipeRepository;
        this.pexelsService = pexelsService;
        this.recipeTagService = recipeTagService;
        this.webClient = webClientBuilder.baseUrl(ingredientServiceUrl).build();
    }

    @Override
    @CachePut(value = "recipesCache", key = "#newRecipe.id")
    public RecipeDto addNewRecipe(CreateRecipeDto createRecipeDto) {
        Recipe newRecipe = RecipeMapper.createRecipeDtoToEntity(createRecipeDto);
        List<IngredientDto> addedIngredients = addIngredientsToIngredientService(createRecipeDto.ingredients());
        List<Integer> ingredientIds = addedIngredients.stream()
                .map(IngredientDto::id)
                .collect(Collectors.toList());
        newRecipe.setIngredients(ingredientIds);
        Recipe savedRecipe = recipeRepository.save(newRecipe);
        recipeTagService.generateRecipeTags(createRecipeDto, savedRecipe.getId());
        return RecipeMapper.toDto(savedRecipe);
    }



    private List<IngredientDto> addIngredientsToIngredientService(List<IngredientDto> ingredientDtos) {
        return this.webClient.post()
                .uri("/api/v2/recipe-ingredients/batch")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(ingredientDtos))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<IngredientDto>>() {})
                .block();
    }

    @Override
    public List<RecipeDto> searchRecipesByTags(List<String> tags) {
        long count = tags.size();
        List<Integer> recipeIds = recipeRepository.findRecipesIdsByTags(tags, count);
        return recipeRepository
                .findAllById(recipeIds)
                .stream()
                .map(RecipeMapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    @Cacheable(value = "recipes", key = "#pageable")
    public List<RecipeDto> findAllRecipes(Pageable pageable) {
        Page<Recipe> pageResult = recipeRepository.findAll(pageable);
        return pageResult.stream()
                .map(RecipeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RecipeDto> getFavoriteRecipes() {
        return recipeRepository
                .findAllByFavorite(true)
                .stream()
                .map(RecipeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteRecipe(int recipeId) {
        recipeRepository.deleteById(recipeId);
    }

    @Override
    @Cacheable(value = "dailyRecipe", key = "#root.target.getDailyKey()")
    public RecipeDto getDailyRecipe() {
        List<Recipe> recipesList = recipeRepository.findAll();
        Random rand = new Random();
        return RecipeMapper.toDto(recipesList.get(rand.nextInt(recipesList.size())));
    }

    public String getDailyKey() {
        return LocalDate.now().toString();
    }

    @Override
    public Optional<RecipeDto> getRecipeById(int id) {
        Optional<Recipe> recipe = recipeRepository.findByIdWithRelations(id);
        return recipe.map(RecipeMapper::toDto);
    }

    @Override
    public List<IngredientDto> getIngredientsByIds(List<Integer> ingredientIds) {
        Mono<List<IngredientDto>> ingredientsDto = this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v2/recipe-ingredients")
                        .queryParam("ids", ingredientIds.stream()
                                .map(Object::toString)
                                .collect(Collectors.joining(",")))
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {});
        return ingredientsDto.block();
    }

    @Override
    public List<RecipeDto> searchRecipesByFridgeIngredients() {
        List<String> ingredientsNames = getFridgeIngredientsNames().block();
        List<Integer> recipeIds = recipeRepository.findRecipesByMatchingTags(ingredientsNames);
        return recipeRepository
                .findAllById(recipeIds)
                .stream()
                .map(RecipeMapper::toDto)
                .collect(Collectors.toList());
    }


    private Mono<List<String>> getFridgeIngredientsNames(){
        return this.webClient.get()
                .uri("https://j9kvt6f27i.execute-api.eu-central-1.amazonaws.com/Stage/ingredients")
                .retrieve()
                .bodyToFlux(JsonNode.class)
                .map(jsonNode -> jsonNode.get("name").asText())
                .collect(Collectors.toList());
    }

    @Override
    public List<Recipe> updateRecipeImages() {
        List<Recipe> recipes = recipeRepository.findAll();
        for (Recipe recipe : recipes) {
            try {
                String imageUrl = pexelsService.fetchImageForRecipe(recipe.getName()).block();
                recipe.setImageUrl(imageUrl);
                recipeRepository.save(recipe);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return recipes;
    }


}
