package com.example.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.example.config.DependencyFactory;
import com.example.model.Ingredient;
import com.google.gson.Gson;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GetFridgeIngredientsHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final DynamoDbEnhancedClient dbClient;
    private final String tableName;
    private final TableSchema<Ingredient> ingredientTableSchema;

    public GetFridgeIngredientsHandler() {
        dbClient = DependencyFactory.dynamoDbEnhancedClient();
        tableName = DependencyFactory.tableName();
        ingredientTableSchema = TableSchema.fromBean(Ingredient.class);
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
        Gson gson = new Gson();
        DynamoDbTable<Ingredient> ingredientTable = dbClient.table(tableName, ingredientTableSchema);

        try {
            List<Ingredient> ingredients = ingredientTable.scan(ScanEnhancedRequest.builder().build())
                    .items()
                    .stream()
                    .collect(Collectors.toList());

            String responseBody = gson.toJson(ingredients);
            context.getLogger().log("Get fridge ingredients with status 200");
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(200)
                    .withBody(responseBody)
                    .withHeaders(Collections.singletonMap("Access-Control-Allow-Origin", "*"));
        } catch (Exception e) {
            context.getLogger().log("Error occurred: " + e.getMessage());
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(500)
                    .withBody("{\"error\":\"" + e.getMessage() + "\"}")
                    .withHeaders(Collections.singletonMap("Access-Control-Allow-Origin", "*"));
        }
    }
}
