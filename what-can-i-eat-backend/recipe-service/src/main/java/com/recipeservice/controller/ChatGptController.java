package com.recipeservice.controller;

import java.util.Optional;

import com.recipeservice.config.RequestLimiter;
import com.recipeservice.dto.CreateRecipeDto;
import com.recipeservice.service.ChatGptService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ChatGptController {

    private final ChatGptService chatGptService;
    private final RequestLimiter requestLimiter;
    private static final Logger logger = LogManager.getLogger(ChatGptController.class);

    @Autowired
    public ChatGptController(ChatGptService chatGptService, RequestLimiter requestLimiter) {
        this.chatGptService = chatGptService;
        this.requestLimiter = requestLimiter;
    }

    @PostMapping("/recipes/generate-recipe")
    public ResponseEntity<?> generateRecipe(HttpServletRequest request) {
        String clientIp = request.getRemoteAddr();
        if (!requestLimiter.isRequestAllowed(clientIp)) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Too many requests, try again later.");
        }
        Optional<CreateRecipeDto> recipe = chatGptService.createRecipeFromChatGpt();
        return recipe.map(dto -> {
            logger.info("Connection with ChatGPT successful");
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        }).orElseGet(() -> {
            logger.info("Connection with ChatGPT failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        });
    }
}

