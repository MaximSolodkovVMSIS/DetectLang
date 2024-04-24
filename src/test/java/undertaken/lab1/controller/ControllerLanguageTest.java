package undertaken.lab1.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import undertaken.lab1.entity.Language;
import undertaken.lab1.service.UsefulTextLanguage;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ControllerLanguageTest {

    @Mock
    private UsefulTextLanguage usefulTextLanguage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllLanguages() {
        List<Language> expectedLanguages = Arrays.asList(
                new Language(),
                new Language()
        );

        when(usefulTextLanguage.getAllLanguage()).thenReturn(expectedLanguages);

        ControllerLanguage controller = new ControllerLanguage(usefulTextLanguage);

        List<Language> actualLanguages = controller.getAllLanguages();

        assertEquals(expectedLanguages, actualLanguages);
    }

    @Test
    void getNameLanguageById_LanguageFound() {
        Long id = 1L;
        Language expectedLanguage = new Language();

        when(usefulTextLanguage.getLanguageById(id)).thenReturn(Optional.of(expectedLanguage));

        ControllerLanguage controller = new ControllerLanguage(usefulTextLanguage);

        ResponseEntity<Optional<Language>> responseEntity = controller.getNameLanguageById(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedLanguage, responseEntity.getBody().orElse(null));
    }

    @Test
    void getNameLanguageById_LanguageNotFound() {
        Long id = 1L;
        when(usefulTextLanguage.getLanguageById(id)).thenReturn(Optional.empty());

        ControllerLanguage controller = new ControllerLanguage(usefulTextLanguage);

        ResponseEntity<Optional<Language>> responseEntity = controller.getNameLanguageById(id);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }
}
