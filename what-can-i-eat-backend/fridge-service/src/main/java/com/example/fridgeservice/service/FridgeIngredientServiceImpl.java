package com.example.fridgeservice.service;


import com.example.fridgeservice.dto.BasicIngredientDto;
import com.example.fridgeservice.repository.FridgeIngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Service
public class FridgeIngredientServiceImpl implements FridgeIngredientService {
    private final FridgeIngredientRepository ingredientRepository;


    private final WebClient webClient;

    @Autowired
    public FridgeIngredientServiceImpl(FridgeIngredientRepository ingredientRepository, WebClient.Builder webClientBuilder, @Value("${ingredient.service.url}") String ingredientServiceUrl) {
        this.ingredientRepository = ingredientRepository;
        this.webClient = webClientBuilder.baseUrl(ingredientServiceUrl).build();
    }

    public Optional<BasicIngredientDto> searchIngredient(String name) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/ingredient/name/{ingredientName}").build(name))
                .retrieve()
                .bodyToMono(BasicIngredientDto.class)
                .blockOptional();
    }


}
