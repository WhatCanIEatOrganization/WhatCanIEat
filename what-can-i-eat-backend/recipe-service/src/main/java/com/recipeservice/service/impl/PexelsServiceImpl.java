package com.recipeservice.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.recipeservice.service.PexelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class PexelsServiceImpl implements PexelsService {

    private final WebClient webClient;

    @Autowired
    public PexelsServiceImpl(WebClient.Builder webClientBuilder,
                             @Value("${pexels.api.url}") String apiUrl,
                             @Value("${pexels.api.key}") String apiKey) {
        this.webClient = webClientBuilder.baseUrl(apiUrl).defaultHeader("Authorization", apiKey).build();
    }

    public Mono<String> fetchImageForRecipe(String query) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/search").queryParam("query", query).build())
                .retrieve()
                .bodyToMono(JsonNode.class)
                .mapNotNull(jsonNode -> {
                    JsonNode photosNode = jsonNode.path("photos");
                    if (photosNode.isArray() && photosNode.size() > 0) {
                        JsonNode firstPhotoNode = photosNode.get(0);
                        if (firstPhotoNode != null) {
                            return firstPhotoNode.path("src").path("original").asText();
                        }
                    }
                    return null;
                });
    }
}
