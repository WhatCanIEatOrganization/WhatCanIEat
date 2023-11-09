package com.example.handler;

import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.example.config.CorsConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractFridgeHandler implements RequestHandler<Map<String, Object>, Map<String, Object>> {

    protected final ObjectMapper objectMapper = new ObjectMapper();

    protected Map<String, Object> generateErrorResponse(Exception e) {
        Map<String, Object> response = new HashMap<>();
        response.put("statusCode", 500);
        response.put("body", "Error: " + e.getMessage());
        response.put("headers", CorsConfig.getCorsHeaders());
        return response;
    }

    protected Map<String, String> convertToSimpleMap(Map<String, AttributeValue> item) {
        Map<String, String> simpleMap = new HashMap<>();
        for (Map.Entry<String, AttributeValue> entry : item.entrySet()) {
            AttributeValue value = entry.getValue();
            if (value.s() != null) {
                simpleMap.put(entry.getKey(), value.s());
            }
        }
        return simpleMap;
    }
}