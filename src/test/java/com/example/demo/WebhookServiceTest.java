package com.example.demo;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

class WebhookServiceTest {

    @Mock
    private RestTemplate restTemplate;

    private WebhookService webhookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        webhookService = new WebhookService(restTemplate);
    }

    @Test
    void testSuccessfulWebhookCall() {
        String url = "https://example.com/webhook";
        String requestBody = "test data";
        String expectedResponse = "success";

        when(restTemplate.postForEntity(url, requestBody, String.class))
            .thenReturn(ResponseEntity.ok(expectedResponse));

        String response = webhookService.callWebhook(url, requestBody);
        assertEquals(expectedResponse, response);
    }

    @Test
    void testCallWebhookReturnsNullWhenResponseBodyIsNull() {
        String url = "https://example.com/webhook";
        String requestBody = "test data";

        when(restTemplate.postForEntity(url, requestBody, String.class))
            .thenReturn(ResponseEntity.ok(null));

        String response = webhookService.callWebhook(url, requestBody);
        assertNull(response);
    }

    @Test
    void testCallWebhookThrowsExceptionOnServerError() {
        String url = "https://example.com/webhook";
        String requestBody = "test data";

        when(restTemplate.postForEntity(url, requestBody, String.class))
            .thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

        assertThrows(HttpClientErrorException.class, () -> {
            webhookService.callWebhook(url, requestBody);
        });
    }

    @Test
    void testCallWebhookWithEmptyRequestBody() {
        String url = "https://example.com/webhook";
        String requestBody = "";
        String expectedResponse = "empty body";

        when(restTemplate.postForEntity(url, requestBody, String.class))
            .thenReturn(ResponseEntity.ok(expectedResponse));

        String response = webhookService.callWebhook(url, requestBody);
        assertEquals(expectedResponse, response);
    }

    @Test
    void testHandleWebhookWithEmptyString() {
        WebhookService service = new WebhookService(restTemplate);
        assertDoesNotThrow(() -> service.handleWebhook(""));
    }

    @Test
    void testHandleWebhookWithNull() {
        WebhookService service = new WebhookService(restTemplate);
        assertDoesNotThrow(() -> service.handleWebhook(null));
    }
}