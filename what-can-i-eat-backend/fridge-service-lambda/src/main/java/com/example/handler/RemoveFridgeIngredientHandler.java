package com.example.handler;

import com.example.config.DependencyFactory;
import com.example.model.Ingredient;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.Key;

import java.util.Collections;
import java.util.Map;

public class RemoveFridgeIngredientHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final DynamoDbEnhancedClient dbClient;
    private final String tableName;
    private final TableSchema<Ingredient> ingredientTableSchema;

    public RemoveFridgeIngredientHandler() {
        dbClient = DependencyFactory.dynamoDbEnhancedClient();
        tableName = DependencyFactory.tableName();
        ingredientTableSchema = TableSchema.fromBean(Ingredient.class);
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
        DynamoDbTable<Ingredient> ingredientTable = dbClient.table(tableName, ingredientTableSchema);
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(Collections.singletonMap("Access-Control-Allow-Origin", "*"));
        try {
            Map<String, String> pathParameters = request.getPathParameters();
            String ingredientId = pathParameters.get("id");
            Key key = Key.builder()
                    .partitionValue(ingredientId)
                    .build();
            ingredientTable.deleteItem(key);
            context.getLogger().log("Delete fridge ingredient with status 204");
            return response.withStatusCode(204);
        } catch (Exception e) {
            context.getLogger().log("Error occurred: " + e.getMessage());
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(500)
                    .withBody("{\"error\":\"" + e.getMessage() + "\"}")
                    .withHeaders(Collections.singletonMap("Access-Control-Allow-Origin", "*"));
        }
    }
}
