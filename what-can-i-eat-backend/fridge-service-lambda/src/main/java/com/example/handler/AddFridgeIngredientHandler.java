package com.example.handler;

import com.example.config.CorsConfig;
import com.example.dto.IngredientDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class AddFridgeIngredientHandler extends AbstractFridgeHandler implements RequestHandler<Map<String, Object>, Map<String, Object>> {

    private final DynamoDbClient dynamoDb;

    public AddFridgeIngredientHandler() {
        this.dynamoDb = DynamoDbClient.builder()
                .httpClient(ApacheHttpClient.builder().build())
                .build();
    }

    @Override
    public Map<String, Object> handleRequest(Map<String, Object> input, Context context) {
        Map<String, Object> response = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        String uniqueID = UUID.randomUUID().toString();
        try {
            String body = (String) input.get("body");
            IngredientDto ingredient = objectMapper.readValue(body, IngredientDto.class);

            Map<String, AttributeValue> item = new HashMap<>();
            item.put("id", AttributeValue.builder().s(uniqueID).build());
            item.put("name", AttributeValue.builder().s(ingredient.name()).build());
            item.put("amount", AttributeValue.builder().s(ingredient.amount()).build());
            item.put("type", AttributeValue.builder().s(ingredient.type()).build());
            PutItemRequest putItemRequest = PutItemRequest.builder()
                    .tableName("fridge-service")
                    .item(item)
                    .build();
            dynamoDb.putItem(putItemRequest);
            response.put("statusCode", 200);
            response.put("body", "Ingredient added successfully");
            response.put("headers", CorsConfig.getCorsHeaders());
        } catch (Exception e) {
            e.printStackTrace();
            response.put("statusCode", 500);
            response.put("body", "Error: " + e.getMessage());
        }
        return response;
    }
}