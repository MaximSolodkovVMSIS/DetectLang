package undertaken.lab1.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import undertaken.lab1.dto.LanguageDetectionRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class LanguageDetectiveServiceTest {

    @Mock
    private RestTemplate restTemplate;
    private LanguageDetectiveService service;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new LanguageDetectiveService(restTemplate);
    }

    @Test
    void detectLanguage() {
        String apiKey = "testApiKey";
        String text = "Test text";
        String responseBody = "{\"data\":{\"detections\":[{\"language\":\"en\"}]}}"; // пример ответа от сервера
        when(restTemplate.exchange(any(String.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(String.class)))
                .thenReturn(new ResponseEntity<>(responseBody, HttpStatus.OK));

        LanguageDetectiveService service = new LanguageDetectiveService(restTemplate);

        String detectedLanguage = service.detectLanguage(new LanguageDetectionRequest(text), apiKey);

        assertEquals("en", detectedLanguage);
    }

    @Test
    void detectLanguage_Failed() {
        // Arrange
        String apiKey = "testApiKey";
        String text = "Test text";
        when(restTemplate.exchange(any(String.class), eq(HttpMethod.GET), any(HttpEntity.class), eq(String.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));

        // Act
        String detectedLanguage = service.detectLanguage(new LanguageDetectionRequest(text), apiKey);

        // Assert
        assertEquals("Language detection failed", detectedLanguage);
    }
}