package com.recipeservice.controller;

import java.util.Optional;
import com.recipeservice.dto.CreateRecipeDto;
import com.recipeservice.service.ChatGptService;
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
    private static final Logger logger = LogManager.getLogger(ChatGptController.class);

    @Autowired
    public ChatGptController(ChatGptService chatGptService) {
        this.chatGptService = chatGptService;
    }

    @PostMapping("/chat")
    public ResponseEntity<CreateRecipeDto> chat() {
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

