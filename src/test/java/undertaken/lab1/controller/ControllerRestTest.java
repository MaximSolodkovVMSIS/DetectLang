package undertaken.lab1.controller;

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