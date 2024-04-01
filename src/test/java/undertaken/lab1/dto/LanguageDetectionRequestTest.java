package undertaken.lab1.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LanguageDetectionRequestTest {

    @Test
    void getText_ReturnsCorrectText() {
        String expectedText = "This is a test text.";
        LanguageDetectionRequest request = new LanguageDetectionRequest(expectedText);
        String actualText = request.getText();
        assertEquals(expectedText, actualText);
    }
}
