package example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import java.util.HashMap;
import java.util.Map;

public class HelloWorldHandler implements RequestHandler<Map<String, Object>, Map<String, Object>> {

  @Override
  public Map<String, Object> handleRequest(Map<String, Object> event, Context context) {
    LambdaLogger logger = context.getLogger();
    logger.log("Otrzymane zdarzenie: " + event);

    Map<String, Object> response = new HashMap<>();
    response.put("statusCode", 200);
    Map<String, String> headers = new HashMap<>();
    headers.put("Content-Type", "text/plain");
    response.put("headers", headers);
    response.put("body", "Hello, World!");

    return response;
  }
}
