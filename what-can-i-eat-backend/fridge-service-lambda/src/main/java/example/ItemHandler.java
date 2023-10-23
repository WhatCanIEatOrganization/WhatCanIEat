package example;

import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.HashMap;
import java.util.Map;

public class ItemHandler implements RequestHandler<Map<String, Object>, Map<String, Object>> {

  private final DynamoDbClient dynamoDb;

  public ItemHandler() {
    this.dynamoDb = DynamoDbClient.builder()
            .httpClient(ApacheHttpClient.builder().build())
            .build();
  }

  @Override
  public Map<String, Object> handleRequest(Map<String, Object> input, Context context) {
    LambdaLogger logger = context.getLogger();
    Map<String, Object> response = new HashMap<>();

    Map<String, AttributeValue> item = new HashMap<>();
    item.put("id", AttributeValue.builder().s("1").build());
    item.put("name", AttributeValue.builder().s("TestName").build());

    PutItemRequest putItemRequest = PutItemRequest.builder()
            .tableName("fridge-service")
            .item(item)
            .build();

    try {
      dynamoDb.putItem(putItemRequest);
      logger.log("Successfully added sample item.");
    } catch (Exception e) {
      logger.log("Error adding sample item: " + e.getMessage());
      response.put("statusCode", 500);
      response.put("body", "Failed to add item.");
      return response;
    }

    Map<String, AttributeValue> key = new HashMap<>();
    key.put("id", AttributeValue.builder().s("1").build());

    GetItemRequest getItemRequest = GetItemRequest.builder()
            .tableName("fridge-service")
            .key(key)
            .build();

    try {
      Map<String, AttributeValue> returnedItem = dynamoDb.getItem(getItemRequest).item();

      if (returnedItem != null && !returnedItem.isEmpty()) {
        logger.log("Successfully retrieved sample item.");
        response.put("statusCode", 200);
        response.put("body", "Retrieved item: " + returnedItem.toString());
      } else {
        logger.log("Sample item not found.");
        response.put("statusCode", 404);
        response.put("body", "Item not found.");
      }
    } catch (Exception e) {
      logger.log("Error retrieving sample item: " + e.getMessage());
      response.put("statusCode", 500);
      response.put("body", "Failed to retrieve item.");
    }

    return response;
  }
}
