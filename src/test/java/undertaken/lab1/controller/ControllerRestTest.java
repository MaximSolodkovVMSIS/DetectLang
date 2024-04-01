package undertaken.lab1.controller;

import com.jayway.jsonpath.spi.cache.Cache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import undertaken.lab1.cache.LanguageCache;
import undertaken.lab1.exception.EndpointActionLogger;
import undertaken.lab1.service.CrudOperation;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ControllerRestTest {
    @Mock
    private CrudOperation crudOperation;

    @Mock
    private EndpointActionLogger endpointActionLogger;

    @Mock
    private LanguageCache cache;

    @InjectMocks
    private ControllerRest controllerRest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void addText() {
        String inputText = "Test+Text";
        String expectedOutput = "Processed Text";

        // Prepare request body
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("text", inputText);

        // Mock behavior of crudOperation.addTextAndDetectLanguage()
        when(crudOperation.addTextAndDetectLanguage(inputText)).thenReturn(expectedOutput);

        // Call the method under test
        String result = controllerRest.addText(requestBody);

        // Verify the result
        assertEquals(expectedOutput, result);
    }

    @Test
    void deleteText() {
        // Prepare test data
        String value = "TestValue";

        // Prepare request body
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("value", value);

        // Mock behavior of crudOperation.deleteText()
        when(crudOperation.deleteText(value)).thenReturn("Deleted");

        // Call the method under test
        String result = controllerRest.deleteText(requestBody);

        // Verify the result
        assertEquals("Deleted", result);

        // Verify endpointActionLogger.logDeleteTextAction() was called with correct value
        verify(endpointActionLogger).logDeleteTextAction(value);

        // Verify crudOperation.deleteText() was called with correct value
        verify(crudOperation).deleteText(value);
    }

    @Test
    void deleteLanguage() {
        String value = "TestLanguage";

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("value", value);

        when(crudOperation.deleteLanguageAndText(value)).thenReturn("Deleted");

        String result = controllerRest.deleteLanguage(requestBody);

        assertEquals("Deleted", result);

        verify(endpointActionLogger).logDeleteLanguageAction(value);
        verify(crudOperation).deleteLanguageAndText(value);
    }

    @Test
    void printCache() {
        controllerRest.printCache();

        verify(cache).printCache();
    }
}