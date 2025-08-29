package com.example.demo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.Map;

@SpringBootApplication
public class WebhookApp implements CommandLineRunner {

    private final RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) {
        SpringApplication.run(WebhookApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String generateWebhookUrl = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";

        // Request body for generateWebhook call
        Map<String, String> request = Map.of(
                "name", "John Doe",
                "regNo", "REG12347",
                "email", "john@example.com"
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String,String>> entity = new HttpEntity<>(request, headers);

        // 1. Call generateWebhook to get webhook URL and accessToken
        ResponseEntity<Map> response = restTemplate.postForEntity(generateWebhookUrl, entity, Map.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            String webhookUrl = (String) response.getBody().get("webhook");
            String accessToken = (String) response.getBody().get("accessToken");

            // 2. Prepare the final SQL query solution
            String finalSqlQuery = "SELECT \n" +
                    "  e1.EMP_ID,\n" +
                    "  e1.FIRST_NAME,\n" +
                    "  e1.LAST_NAME,\n" +
                    "  d.DEPARTMENT_NAME,\n" +
                    "  COUNT(e2.EMP_ID) AS YOUNGER_EMPLOYEES_COUNT\n" +
                    "FROM EMPLOYEE e1\n" +
                    "JOIN DEPARTMENT d ON e1.DEPARTMENT = d.DEPARTMENT_ID\n" +
                    "LEFT JOIN EMPLOYEE e2 \n" +
                    "  ON e1.DEPARTMENT = e2.DEPARTMENT\n" +
                    "  AND e2.DOB > e1.DOB\n" +
                    "GROUP BY e1.EMP_ID, e1.FIRST_NAME, e1.LAST_NAME, d.DEPARTMENT_NAME\n" +
                    "ORDER BY e1.EMP_ID DESC;";

            // 3. Send final SQL query to webhook URL
            HttpHeaders webhookHeaders = new HttpHeaders();
            webhookHeaders.setContentType(MediaType.APPLICATION_JSON);
            webhookHeaders.setBearerAuth(accessToken);

            Map<String, String> webhookRequest = Map.of("finalQuery", finalSqlQuery);

            HttpEntity<Map<String,String>> webhookEntity = new HttpEntity<>(webhookRequest, webhookHeaders);
            ResponseEntity<String> webhookResponse = restTemplate.postForEntity(webhookUrl, webhookEntity, String.class);

            if (webhookResponse.getStatusCode().is2xxSuccessful()) {
                System.out.println("SQL Query solution sent successfully.");
            } else {
                System.err.println("Failed to send SQL Query solution: " + webhookResponse.getStatusCode());
            }
        } else {
            System.err.println("Failed to generate webhook: " + response.getStatusCode());
        }
    }
}


