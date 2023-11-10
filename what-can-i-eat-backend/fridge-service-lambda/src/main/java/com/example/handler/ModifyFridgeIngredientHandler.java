package com.example.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.example.config.CorsConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.HashMap;
import java.util.Map;

public class ModifyFridgeIngredientHandler extends AbstractFridgeHandler implements RequestHandler<Map<String, Object>, Map<String, Object>> {

    private final DynamoDbClient dynamoDb;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ModifyFridgeIngredientHandler() {
        this.dynamoDb = DynamoDbClient.builder()
                .httpClient(ApacheHttpClient.builder().build())
                .build();
    }

    @Override
    public Map<String, Object> handleRequest(Map<String, Object> input, Context context) {
        Map<String, Object> response = new HashMap<>();
        try {
            String jsonStringBody = (String) input.get("body");
            Map<String, Object> body = objectMapper.readValue(jsonStringBody, Map.class);
            String ingredientId = (String) body.get("id");
            String name = (String) body.get("name");
            String amountStr = (String) body.get("amount");
            Number amount = null;
            if (amountStr != null) {
                amount = Double.parseDouble(amountStr);
            }
            String type = (String) body.get("type");
            Map<String, AttributeValue> key = new HashMap<>();
            key.put("id", AttributeValue.builder().s(ingredientId).build());
            Map<String, String> attributeNames = new HashMap<>();
            Map<String, AttributeValue> attributeValues = new HashMap<>();
            String updateExpression = "set";
            if (name != null) {
                updateExpression += " #n = :name,";
                attributeNames.put("#n", "name");
                attributeValues.put(":name", AttributeValue.builder().s(name).build());
            }
            if (amount != null) {
                updateExpression += " amount = :amount,";
                attributeValues.put(":amount", AttributeValue.builder().n(amount.toString()).build());
            }
            if (type != null) {
                updateExpression += " #t = :type,";
                attributeNames.put("#t", "type");
                attributeValues.put(":type", AttributeValue.builder().s(type).build());
            }
            updateExpression = updateExpression.replaceAll(",$", "");
            UpdateItemRequest updateItemRequest = UpdateItemRequest.builder()
                    .tableName("fridge-service")
                    .key(key)
                    .updateExpression(updateExpression)
                    .expressionAttributeNames(attributeNames)
                    .expressionAttributeValues(attributeValues)
                    .build();
            dynamoDb.updateItem(updateItemRequest);
            response.put("statusCode", 200);
            response.put("body", "{\"message\":\"Ingredient modified successfully\"}");
            response.put("headers", CorsConfig.getCorsHeaders());
        } catch (Exception e) {
            context.getLogger().log("Error: " + e.getMessage());
            response.put("statusCode", 500);
            response.put("body", "{\"error\":\"" + e.getMessage() + "\"}");
        }
        return response;
    }
}
