package com.recipeservice.controller;

import java.util.List;

import com.recipeservice.dto.ChatGptMessageDto;
import com.recipeservice.dto.ChatGptRequestDto;
import com.recipeservice.dto.ChatGptResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/v1")
public class ChatGptController {

    private final RestTemplate restTemplate;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiUrl;

    @Autowired
    public ChatGptController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/chat")
    public ChatGptResponseDto chat(@RequestParam("prompt") String prompt) {

        ChatGptRequestDto request = new ChatGptRequestDto(model,
                List.of(new ChatGptMessageDto("user", prompt)));

        return restTemplate.postForObject(apiUrl, request, ChatGptResponseDto.class);
    }
}