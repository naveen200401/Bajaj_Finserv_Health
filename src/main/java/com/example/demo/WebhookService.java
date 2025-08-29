package com.example.demo;

import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

public class WebhookService {
    private final RestTemplate restTemplate;

    public WebhookService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String callWebhook(String url, String requestBody) {
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestBody, String.class);
        return response.getBody();
    }

    public void handleWebhook(String request) {
        // Dummy implementation for test compilation
        // You can add logic here if needed for your application
        if (request == null) {
            // handle null case if needed
        }
        // else do nothing
    }
}