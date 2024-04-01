package undertaken.lab1.controller;

import com.jayway.jsonpath.spi.cache.Cache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import undertaken.lab1.cache.LanguageCache;
import undertaken.lab1.dto.ParameterList;
import undertaken.lab1.exception.EndpointActionLogger;
import undertaken.lab1.service.CrudOperation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
            // Arrange
            ParameterList parameterList = new ParameterList();
            List<String> texts = Arrays.asList("Test Text 1", "Test Text 2");
            parameterList.setParameters(texts);

            List<String> expectedOutput = Arrays.asList("Processed Text 1", "Processed Text 2");

            CrudOperation crudOperation = mock(CrudOperation.class);
            when(crudOperation.addTextsAndDetectLanguage(texts)).thenReturn(expectedOutput);

            ControllerRest controllerRest = new ControllerRest(
                null, // LanguageDetectiveService
                null, // ServiceApiKey
                null, // NameLanguageService
                null, // TextLanguageService
                crudOperation, // CrudOperation
                null, // LanguageCache
                null // EndpointActionLogger
        );

            // Act
            List<String> result = controllerRest.addTexts(parameterList);

            // Assert
            assertEquals(expectedOutput, result);
            verify(crudOperation, times(1)).addTextsAndDetectLanguage(texts);
    }

    @Test
    void deleteText() {
        String value = "TestValue";

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("value", value);

        when(crudOperation.deleteText(value)).thenReturn("Deleted");

        String result = controllerRest.deleteText(requestBody);

        assertEquals("Deleted", result);

        verify(endpointActionLogger).logDeleteTextAction(value);

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