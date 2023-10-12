package com.recipeservice.service;

import reactor.core.publisher.Mono;

public interface PexelsService {
    Mono<String> fetchImageForRecipe(String query);
}
