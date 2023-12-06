package com.recipeservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


@Component
public class RequestLimiter {
    @Value("${request.limiter.time}")
    private Integer limitTime;
    private final ConcurrentHashMap<String, LocalDateTime> requestMap = new ConcurrentHashMap<>();
    private static final Logger logger = LogManager.getLogger(RequestLimiter.class);

    public boolean isRequestAllowed(String ip) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime lastRequestTime = requestMap.compute(ip, (key, value) -> {
            if (value == null || value.plusMinutes(limitTime).isBefore(now)) {
                return now;
            }
            return value;
        });

        boolean allowed = lastRequestTime.equals(now);
        logger.info("Request from IP " + ip + " is " + (allowed ? "allowed" : "denied"));
        return allowed;
    }
}
