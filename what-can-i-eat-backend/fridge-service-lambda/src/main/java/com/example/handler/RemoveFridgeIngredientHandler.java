package com.example.handler;

import com.example.config.CorsConfig;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.DeleteItemRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.HashMap;
import java.util.Map;

public class RemoveFridgeIngredientHandler extends AbstractFridgeHandler implements RequestHandler<Map<String, Object>, Map<String, Object>> {

    private final DynamoDbClient dynamoDb;

    public RemoveFridgeIngredientHandler() {
        this.dynamoDb = DynamoDbClient.builder()
                .httpClient(ApacheHttpClient.builder().build())
                .build();
    }

    @Override
    public Map<String, Object> handleRequest(Map<String, Object> input, Context context) {
        Map<String, Object> response = new HashMap<>();
        try {
            Map<String, String> pathParameters = (Map<String, String>) input.get("pathParameters");
            String ingredientId = pathParameters.get("id");

            Map<String, AttributeValue> key = new HashMap<>();
            key.put("id", AttributeValue.builder().s(ingredientId).build());

            DeleteItemRequest deleteItemRequest = DeleteItemRequest.builder()
                    .tableName("fridge-service")
                    .key(key)
                    .build();
            dynamoDb.deleteItem(deleteItemRequest);

            response.put("statusCode", 200);
            response.put("body", "{\"message\":\"Ingredient removed successfully\"}");
            response.put("headers", CorsConfig.getCorsHeaders());
        } catch (Exception e) {
            context.getLogger().log("Error: " + e.getMessage());
            response.put("statusCode", 500);
            response.put("body", "{\"error\":\"" + e.getMessage() + "\"}");
        }
        return response;
    }
}
