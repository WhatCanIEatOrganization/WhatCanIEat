package com.example.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.example.config.CorsConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GetFridgeIngredientsHandler extends AbstractFridgeHandler implements RequestHandler<Map<String, Object>, Map<String, Object>> {

    private final DynamoDbClient dynamoDb;

    public GetFridgeIngredientsHandler() {
        this.dynamoDb = DynamoDbClient.builder()
                .httpClient(ApacheHttpClient.builder().build())
                .build();
    }

    @Override
    public Map<String, Object> handleRequest(Map<String, Object> input, Context context) {
        ObjectMapper objectMapper = new ObjectMapper();
        LambdaLogger logger = context.getLogger();
        Map<String, Object> response = new HashMap<>();
        try {
            logger.log("Fetching all ingredients from DynamoDB...");
            List<Map<String, AttributeValue>> items = fetchAllIngredientsFromDynamoDB();
            List<Map<String, String>> ingredients = items.stream()
                    .map(this::convertToSimpleMap)
                    .collect(Collectors.toList());

            response.put("statusCode", 200);
            response.put("body", objectMapper.writeValueAsString(ingredients));
            response.put("headers", CorsConfig.getCorsHeaders());
            logger.log("Successfully fetched ingredients.");
        } catch (Exception e) {
            logger.log("Error occurred: " + e.getMessage());
            response.put("statusCode", 500);
            response.put("body", "{\"error\":\"" + e.getMessage() + "\"}");
        }
        return response;
    }

    private List<Map<String, AttributeValue>> fetchAllIngredientsFromDynamoDB() {
        ScanRequest scanRequest = ScanRequest.builder()
                .tableName("fridge-service")
                .build();
        ScanResponse scanResponse = dynamoDb.scan(scanRequest);
        return scanResponse.items();
    }
}
