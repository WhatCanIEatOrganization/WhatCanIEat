package example;

import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ItemHandler implements RequestHandler<Map<String, Object>, Map<String, Object>> {

  private static final String INGREDIENT_SERVICE_URL = "";
  private final DynamoDbClient dynamoDb;
  private final HttpClient httpClient;
  private final ObjectMapper objectMapper;

  public ItemHandler() {
    this.dynamoDb = DynamoDbClient.builder()
            .httpClient(ApacheHttpClient.builder().build())
            .build();
    this.httpClient = HttpClient.newHttpClient();
    this.objectMapper = new ObjectMapper();
  }

  @Override
  public Map<String, Object> handleRequest(Map<String, Object> input, Context context) {
    Map<String, Object> response = new HashMap<>();
    try {
      HttpRequest request = HttpRequest.newBuilder()
              .uri(new URI(INGREDIENT_SERVICE_URL))
              .GET()
              .build();
      HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
      String responseBody = httpResponse.body();
      BasicIngredientDto[] ingredients = objectMapper.readValue(responseBody, BasicIngredientDto[].class);

      int limit = Math.min(ingredients.length, 5);
      for (int i = 0; i < limit; i++) {
        BasicIngredientDto ingredient = ingredients[i];
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("id", AttributeValue.builder().s("1").build());
        item.put("name", AttributeValue.builder().s(ingredient.name()).build());
        item.put("description", AttributeValue.builder().s(ingredient.description()).build());
        item.put("imageUrl", AttributeValue.builder().s(ingredient.imageUrl()).build());

        PutItemRequest putItemRequest = PutItemRequest.builder()
                .tableName("fridge-service")
                .item(item)
                .build();
        dynamoDb.putItem(putItemRequest);
      }

      response.put("statusCode", 200);
      response.put("body", "Retrieved items: " + limit);

    } catch (Exception e) {
      e.printStackTrace();
      response.put("statusCode", 500);
      response.put("body", "Error: " + e.getMessage());
    }

    return response;
  }
}