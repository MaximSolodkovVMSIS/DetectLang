package undertaken.lab1.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ServiceApiKey {
    @Value("${api.key}")
    private String apiKey;

    public String getApiKey() {
        return apiKey;
    }
}
