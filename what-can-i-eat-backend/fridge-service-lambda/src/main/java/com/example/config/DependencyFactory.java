package com.example.config;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class DependencyFactory {

    public static DynamoDbEnhancedClient dynamoDbEnhancedClient() {
        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(DynamoDbClient.builder()
                        .httpClient(ApacheHttpClient.builder().build())
                        .build())
                .build();
    }

    public static String tableName() {
        return "fridge-service";
    }
}
