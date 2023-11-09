package com.recipeservice.service.impl;


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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class RecipeServiceImpl implements RecipeService {



    private final RecipeRepository recipeRepository;
    private final PexelsService pexelsService;
    private final RecipeTagService recipeTagService;
    final WebClient webClient;


    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository, PexelsService pexelsService, RecipeTagService recipeTagService, WebClient.Builder webClientBuilder,
                             @Value("${ingredient.service.url}") String ingredientServiceUrl) {
        this.recipeRepository = recipeRepository;
        this.pexelsService = pexelsService;
        this.recipeTagService = recipeTagService;
        this.webClient = webClientBuilder.baseUrl(ingredientServiceUrl).build();
    }

    @Override
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
    public List<RecipeDto> getRecipesList() {
        return recipeRepository
                .findAllWithRelations()
                .stream()
                .limit(25)
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
    public RecipeDto getRandomRecipe() {
        List<Recipe> recipesList = recipeRepository.findAll();
        Random rand = new Random();
        return RecipeMapper.toDto(recipesList.get(rand.nextInt(recipesList.size())));
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
                        .queryParam("ids", String.join(",", ingredientIds.stream().map(Object::toString).collect(Collectors.toList())))
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {});
        return ingredientsDto.block();
    }


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
