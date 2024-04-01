package undertaken.lab1.dto;

public class LanguageDetectionRequest {
    private String text;
    public LanguageDetectionRequest(String text) {
        this.text = text;
    }
    public String getText() {
        return text;
    }
}

