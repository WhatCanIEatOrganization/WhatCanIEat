package com.example.fridgeservice.service;


import com.example.fridgeservice.dto.BasicIngredientDto;
import com.example.fridgeservice.dto.FridgeIngredientDto;
import com.example.fridgeservice.mapper.FridgeIngredientMapper;
import com.example.fridgeservice.model.FridgeIngredient;
import com.example.fridgeservice.repository.FridgeIngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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

    public Optional<BasicIngredientDto> searchBasicIngredient(String name) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/basic-ingredients/name/{ingredientName}").build(name))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> {
                    // Możesz tutaj dodać logowanie błędu, jeśli chcesz
                    return Mono.empty();
                })
                .onStatus(HttpStatusCode::is5xxServerError, response -> {
                    // Możesz tutaj dodać logowanie błędu, jeśli chcesz
                    return Mono.empty();
                })
                .bodyToMono(BasicIngredientDto.class)
                .blockOptional();
    }

    @Override
    public FridgeIngredientDto addFridgeIngredient(FridgeIngredientDto fridgeIngredientDto) {
        Optional<BasicIngredientDto> basicIngredientDto = searchBasicIngredient(fridgeIngredientDto.name());
        if(basicIngredientDto.isPresent()){
            FridgeIngredient fridgeIngredient = FridgeIngredientMapper.basicIngredientDtoToFridgeIngredient(basicIngredientDto.get());
            fridgeIngredient.setAmountWithUnit(fridgeIngredientDto.amountWithUnit());
            return FridgeIngredientMapper.fridgeIngredientToDto(ingredientRepository
                    .save(fridgeIngredient));
        }
        else {
            FridgeIngredient newIngredient = FridgeIngredientMapper.dtoToFridgeIngredient(fridgeIngredientDto);
            return FridgeIngredientMapper.fridgeIngredientToDto(ingredientRepository.save(newIngredient));
        }
    }


}
