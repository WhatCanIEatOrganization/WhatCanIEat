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

public class ModifyFridgeIngredientHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final DynamoDbEnhancedClient dbClient;
    private final String tableName;
    private final TableSchema<Ingredient> ingredientTableSchema;

    public ModifyFridgeIngredientHandler() {
        dbClient = DependencyFactory.dynamoDbEnhancedClient();
        tableName = DependencyFactory.tableName();
        ingredientTableSchema = TableSchema.fromBean(Ingredient.class);
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
        Gson gson = new Gson();
        Ingredient updatedIngredient = gson.fromJson(request.getBody(), Ingredient.class);
        DynamoDbTable<Ingredient> ingredientTable = dbClient.table(tableName, ingredientTableSchema);
        Ingredient existingIngredient = ingredientTable.getItem(updatedIngredient);
        if (existingIngredient != null) {
            existingIngredient.setName(updatedIngredient.getName());
            existingIngredient.setAmount(updatedIngredient.getAmount());
            existingIngredient.setType(updatedIngredient.getType());
            ingredientTable.updateItem(existingIngredient);
            String responseBody = gson.toJson(existingIngredient);
            context.getLogger().log("Modify fridge ingredient with status 200");
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(200)
                    .withBody(responseBody)
                    .withHeaders(Collections.singletonMap("Access-Control-Allow-Origin", "*"));
        } else {
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(404)
                    .withBody("{\"error\":\"Ingredient not found\"}")
                    .withHeaders(Collections.singletonMap("Access-Control-Allow-Origin", "*"));
        }
    }
}