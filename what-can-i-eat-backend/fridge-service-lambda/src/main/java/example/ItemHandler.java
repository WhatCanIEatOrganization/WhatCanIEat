package example;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.HashMap;
import java.util.Map;

public class ItemHandler implements RequestHandler<Map<String, Object>, Map<String, Object>> {

  private final AmazonDynamoDB dynamoDb;

  public ItemHandler() {
    dynamoDb = AmazonDynamoDBClientBuilder.defaultClient();
  }

  @Override
  public Map<String, Object> handleRequest(Map<String, Object> input, Context context) {
    LambdaLogger logger = context.getLogger();
    Map<String, Object> response = new HashMap<>();

    Map<String, AttributeValue> item = new HashMap<>();
    item.put("id", new AttributeValue("1"));
    item.put("name", new AttributeValue("TestName"));

    PutItemRequest putItemRequest = new PutItemRequest()
            .withTableName("fridge-service")
            .withItem(item);

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
    key.put("id", new AttributeValue("1"));

    GetItemRequest getItemRequest = new GetItemRequest()
            .withTableName("fridge-service")
            .withKey(key);

    try {
      GetItemResult getItemResult = dynamoDb.getItem(getItemRequest);

      if (getItemResult.getItem() != null) {
        logger.log("Successfully retrieved sample item.");
        response.put("statusCode", 200);
        response.put("body", "Retrieved item: " + getItemResult.getItem().toString());
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
