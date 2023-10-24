package example;

import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class AddFridgeIngredientHandler implements RequestHandler<Map<String, Object>, Map<String, Object>> {

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
        try {
            String body = (String) input.get("body");
            BasicIngredientDto ingredient = objectMapper.readValue(body, BasicIngredientDto.class);

            Map<String, AttributeValue> item = new HashMap<>();
            item.put("id", AttributeValue.builder().s(ingredient.id().toString()).build());
            item.put("name", AttributeValue.builder().s(ingredient.name()).build());
            item.put("description", AttributeValue.builder().s(ingredient.description()).build());
            item.put("imageUrl", AttributeValue.builder().s(ingredient.imageUrl()).build());

            PutItemRequest putItemRequest = PutItemRequest.builder()
                    .tableName("fridge-service")
                    .item(item)
                    .build();
            dynamoDb.putItem(putItemRequest);

            response.put("statusCode", 200);
            response.put("body", "Ingredient added successfully");
            Map<String, String> simpleItem = item.entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().s()));
            response.put("ingredient", objectMapper.writeValueAsString(simpleItem));

        } catch (Exception e) {
            e.printStackTrace();
            response.put("statusCode", 500);
            response.put("body", "Error: " + e.getMessage());
        }
        return response;
    }
}