package com.example.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.example.model.Ingredient;
import com.example.config.DependencyFactory;
import com.google.gson.Gson;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.Collections;

public class AddFridgeIngredientHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final DynamoDbEnhancedClient dbClient;
    private final String tableName;
    private final TableSchema<Ingredient> ingredientTableSchema;

    public AddFridgeIngredientHandler() {
        dbClient = DependencyFactory.dynamoDbEnhancedClient();
        tableName = DependencyFactory.tableName();
        ingredientTableSchema = TableSchema.fromBean(Ingredient.class);
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
        Gson gson = new Gson();
        Ingredient ingredient = gson.fromJson(request.getBody(), Ingredient.class);
        context.getLogger().log("Ingredient id: " + ingredient.getId());
        DynamoDbTable<Ingredient> ingredientTable = dbClient.table(tableName, ingredientTableSchema);
        ingredientTable.putItem(ingredient);
        String responseBody = gson.toJson(ingredient);
        return new APIGatewayProxyResponseEvent()
                .withStatusCode(200)
                .withBody(responseBody)
                .withHeaders(Collections.singletonMap("Access-Control-Allow-Origin", "*"));
    }
}
