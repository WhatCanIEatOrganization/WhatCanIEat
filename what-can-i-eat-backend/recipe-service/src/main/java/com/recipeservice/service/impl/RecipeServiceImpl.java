package com.recipeservice.service.impl;


import com.recipeservice.dto.IngredientDto;
import com.recipeservice.dto.RecipeDto;
import com.recipeservice.mapper.RecipeMapper;
import com.recipeservice.model.Recipe;
import com.recipeservice.model.RecipeTag;
import com.recipeservice.repository.RecipeRepository;
import com.recipeservice.repository.RecipeTagRepository;
import com.recipeservice.service.PreparationStepService;
import com.recipeservice.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class RecipeServiceImpl implements RecipeService {



    private final RecipeRepository recipeRepository;
    private final RecipeTagRepository recipeTagRepository;

    private final PreparationStepService preparationStepService;
    private final WebClient webClient;
    @Value("${ingredient.service.url}")
    private String ingredientServiceUrl;


    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeTagRepository recipeTagRepository, PreparationStepService preparationStepService, WebClient.Builder webClientBuilder) {
        this.recipeRepository = recipeRepository;
        this.recipeTagRepository = recipeTagRepository;
        this.preparationStepService = preparationStepService;
        this.webClient = webClientBuilder.baseUrl(ingredientServiceUrl).build();
    }

    @Override
    public RecipeDto addNewRecipe(RecipeDto recipeDto) {
        Recipe savedRecipe = saveRecipe(recipeDto);
        updateRecipeWithNewIngredients(savedRecipe, recipeDto.newIngredients());
        preparationStepService.savePreparationSteps(recipeDto, savedRecipe);
        saveTagsToDatabase(recipeDto.newIngredients(), savedRecipe);
        return RecipeMapper.INSTANCE.recipeToRecipeDto(savedRecipe);
    }

    private void updateRecipeWithNewIngredients(Recipe savedRecipe, List<IngredientDto> newIngredients) {
        List<IngredientDto> addedIngredients = addIngredientsToRecipe(newIngredients);
        savedRecipe.setIngredients(addedIngredients.stream().map(IngredientDto::id).collect(Collectors.toList()));
    }


    private void saveTagsToDatabase(List<IngredientDto> ingredientsDto, Recipe recipe) {
        for (IngredientDto ingredientDto : ingredientsDto) {
            RecipeTag recipeTag = new RecipeTag();
            recipeTag.setRecipe(recipe);
            recipeTag.setTag(ingredientDto.name());
            recipeTagRepository.save(recipeTag);
        }
    }

    private List<IngredientDto> addIngredientsToRecipe(List<IngredientDto> newIngredients) {
        return addIngredientsToIngredientService(newIngredients);
    }

    private List<IngredientDto> addIngredientsToIngredientService(List<IngredientDto> ingredientDtos) {
        return this.webClient.post()
                .uri("/ingredient/batch")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(ingredientDtos))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<IngredientDto>>() {})
                .block();
    }

    private Recipe saveRecipe(RecipeDto recipeDto) {
        Recipe mappedRecipe = RecipeMapper.INSTANCE.recipeDtoToRecipe(recipeDto);
        return recipeRepository.save(mappedRecipe);
    }

    @Override
    public List<Recipe> getRecipesByIngredients(List<String> tags) {
        long count = tags.size();
        List<Integer> recipeIds = recipeRepository.findRecipeIdsByTags(tags, count);
        return recipeRepository.findAllById(recipeIds);
    }


    @Override
    public List<RecipeDto> getRecipesList() {
        return recipeRepository
                .findAll()
                .stream()
                .map(RecipeMapper.INSTANCE::recipeToRecipeDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RecipeDto> getFavoriteRecipes() {
        return recipeRepository
                .findAllByFavorite(true)
                .stream()
                .map(RecipeMapper.INSTANCE::recipeToRecipeDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteRecipe(int recipeId) {
        recipeRepository.deleteById(recipeId);
    }

    @Override
    public RecipeDto getRandomRecipe() {
        List<Recipe> recipesList = recipeRepository.findAll();
        Random rand = new Random();
        return RecipeMapper.INSTANCE.recipeToRecipeDto(recipesList.get(rand.nextInt(recipesList.size())));
    }

    @Override
    public Recipe getRecipeById(int id) {
        return recipeRepository.findById(id).get();
    }

    @Override
    public List<IngredientDto> getIngredientsByIds(List<Integer> ingredientIds) {
        Mono<List<IngredientDto>> ingredientsDto = this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/ingredient")
                        .queryParam("ids", String.join(",", ingredientIds.stream().map(Object::toString).collect(Collectors.toList())))
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {});
        return ingredientsDto.block();
    }


}
