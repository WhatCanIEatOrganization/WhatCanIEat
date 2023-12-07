package com.recipeservice.service;

import com.recipeservice.dto.CreateRecipeDto;
import com.recipeservice.dto.chatgpt.ChatGptResponseDto;

import java.util.Optional;

public interface ChatGptService {
    String buildPrompt(String ingredientsNames);
    Optional<CreateRecipeDto> parseResponse(ChatGptResponseDto response);
    Optional<CreateRecipeDto> createRecipeFromChatGpt();

}
