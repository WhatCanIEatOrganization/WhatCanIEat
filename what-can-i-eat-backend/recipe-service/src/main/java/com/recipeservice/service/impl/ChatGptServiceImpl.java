package com.recipeservice.service.impl;

import com.recipeservice.dto.CreateRecipeDto;
import com.recipeservice.dto.IngredientDto;
import com.recipeservice.dto.PreparationStepDto;
import com.recipeservice.dto.chatgpt.ChatGptMessageDto;
import com.recipeservice.dto.chatgpt.ChatGptRequestDto;
import com.recipeservice.dto.chatgpt.ChatGptResponseDto;
import com.recipeservice.service.ChatGptService;
import com.recipeservice.service.RecipeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ChatGptServiceImpl implements ChatGptService {

    private final RestTemplate restTemplate;
    private final RecipeService recipeService;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiUrl;

    private static final Logger logger = LogManager.getLogger(ChatGptServiceImpl.class);

    @Autowired
    public ChatGptServiceImpl(RestTemplate restTemplate, RecipeService recipeService) {
        this.restTemplate = restTemplate;
        this.recipeService = recipeService;
    }
    @Override
    public String buildPrompt(String ingredientsNames) {
        return String.format("Create a complete recipe in English using these ingredients: %s. " +
                "Assume common spices and basic cooking ingredients are also available. " +
                "The recipe format should be: " +
                "Recipe Name: <Name>; " +
                "Ingredients: 1. <Amount with unit> <Ingredient>, 2. <Amount with unit> <Ingredient>, etc.; " +
                "Preparation Steps: 1. <Step>, 2. <Step>, etc.", ingredientsNames);
    }


    @Override
    public Optional<CreateRecipeDto> createRecipeFromChatGpt() {
        try {
            String ingredientsNames = String.join(", ", Objects.requireNonNull(recipeService.getFridgeIngredientsNames().block()));
            String prompt = buildPrompt(ingredientsNames);
            ChatGptRequestDto request = new ChatGptRequestDto(model, List.of(new ChatGptMessageDto("user", prompt)));
            ChatGptResponseDto response = restTemplate.postForObject(apiUrl, request, ChatGptResponseDto.class);
            return parseResponse(response);
        } catch (Exception e) {
            logger.error("Error while creating recipe from ChatGPT: " + e.getMessage(), e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<CreateRecipeDto> parseResponse(ChatGptResponseDto response) {
        try {
            if (response == null || response.getChoices().isEmpty() || response.getChoices().get(0).getMessage() == null) {
                logger.info("Empty response or no messages in response");
                return Optional.empty();
            }
            String responseContent = response.getChoices().get(0).getMessage().getContent();
            String name = extractRecipeName(responseContent);
            List<IngredientDto> ingredients = extractIngredients(responseContent);
            List<PreparationStepDto> preparationSteps = extractPreparationSteps(responseContent);
            CreateRecipeDto recipeDto = new CreateRecipeDto(null, name, "", false, "", null, null, null, null, "", preparationSteps, ingredients);
            logger.info("Successful response parsing");
            return Optional.of(recipeDto);
        } catch (Exception e) {
            logger.info("Error:" + e);
            return Optional.empty();
        }
    }

    private String extractRecipeName(String responseContent) throws Exception {
        int nameEndIndex = responseContent.indexOf("\n\nIngredients:");
        if (nameEndIndex == -1) {
            logger.info("Invalid recipe name section");
            throw new Exception("Invalid response format - missing Ingredients section");
        }
        logger.info("Correct recipe name section");
        return responseContent.substring("Recipe Name: ".length(), nameEndIndex).trim();
    }

    private List<IngredientDto> extractIngredients(String responseContent) throws Exception {
        int ingredientsStartIndex = responseContent.indexOf("\n\nIngredients:") + "\n\nIngredients:\n".length();
        int ingredientsEndIndex = responseContent.indexOf("\n\nPreparation Steps:");
        if (ingredientsEndIndex == -1) {
            logger.info("Invalid ingredients section");
            throw new Exception("Invalid response format - missing Preparation Steps section");
        }
        String ingredientsSection = responseContent.substring(ingredientsStartIndex, ingredientsEndIndex).trim();
        logger.info("Correct ingredients section");
        return Arrays.stream(ingredientsSection.split("\n"))
                .map(line -> {
                    String[] parts = line.split("\\. ", 2);
                    return new IngredientDto(null, parts[1].trim(), "", "", "");
                })
                .collect(Collectors.toList());
    }

    private List<PreparationStepDto> extractPreparationSteps(String responseContent) {
        int stepsStartIndex = responseContent.indexOf("\n\nPreparation Steps:") + "\n\nPreparation Steps:\n".length();
        String stepsSection = responseContent.substring(stepsStartIndex).trim();
        logger.info("Correct preparation steps section");
        return Arrays.stream(stepsSection.split("\n"))
                .filter(step -> step.length() > 3)
                .map(step -> new PreparationStepDto(null, step.substring(3).trim()))
                .collect(Collectors.toList());
    }
}
