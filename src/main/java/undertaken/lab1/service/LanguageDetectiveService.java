package undertaken.lab1.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import undertaken.lab1.dto.LanguageDetectionRequest;


@Service
public class LanguageDetectiveService {
    private final RestTemplate restTemplate;

    private static final String DETECTIONS = "detections";

    private static final Logger logger = LoggerFactory.getLogger(LanguageDetectiveService.class);


    public LanguageDetectiveService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String detectLanguage(LanguageDetectionRequest request, String apiKey) {
        return sendLanguageDetectionRequest(request.getText(), apiKey);
    }

    public String sendLanguageDetectionRequest(String text, String apiKey) {
        String url = "https://ws.detectlanguage.com/0.2/detect?q=" + text;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey); //тип токена доступа
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {  //статус код ответа
            String responseBody = response.getBody();
            if (responseBody != null) {
                return extractLanguage(responseBody);
            }
        }
        return "Language detection failed";
    }

    private String extractLanguage(String responseBody) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseBody);
            if (rootNode.has("data")) {
                JsonNode dataNode = rootNode.get("data");
                if (dataNode.has(DETECTIONS) && dataNode.get(DETECTIONS).isArray()) {
                    JsonNode detectionNode = dataNode.get(DETECTIONS).get(0);
                    if (detectionNode.has("language")) {
                        return detectionNode.get("language").asText();
                    }
                }
            }
        } catch (Exception e) {
            logger.error("An error occurred while extracting language", e);
        }
        return "Language detection failed";
    }

}