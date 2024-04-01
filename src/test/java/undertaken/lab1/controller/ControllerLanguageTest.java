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

    private ControllerLanguage controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new ControllerLanguage(usefulTextLanguage);
    }


    @Test
    void getAllLanguages() {
        // Arrange
        List<Language> expectedLanguages = Arrays.asList(
                new Language(),
                new Language()
        );
        // Optionally, you can set id and name properties of Language objects here

        when(usefulTextLanguage.getAllLanguage()).thenReturn(expectedLanguages);

        ControllerLanguage controller = new ControllerLanguage(usefulTextLanguage);

        // Act
        List<Language> actualLanguages = controller.getAllLanguages();

        // Assert
        assertEquals(expectedLanguages, actualLanguages);
    }

    @Test
    void getNameLanguageById_LanguageFound() {
        // Arrange
        Long id = 1L;
        Language expectedLanguage = new Language();
        // Optionally, you can set id and name properties of Language object here
        when(usefulTextLanguage.getLanguageById(id)).thenReturn(Optional.of(expectedLanguage));

        ControllerLanguage controller = new ControllerLanguage(usefulTextLanguage);

        // Act
        ResponseEntity<Optional<Language>> responseEntity = controller.getNameLanguageById(id);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedLanguage, responseEntity.getBody().orElse(null));
    }

    @Test
    void getNameLanguageById_LanguageNotFound() {
        // Arrange
        Long id = 1L;
        when(usefulTextLanguage.getLanguageById(id)).thenReturn(Optional.empty());

        ControllerLanguage controller = new ControllerLanguage(usefulTextLanguage);

        // Act
        ResponseEntity<Optional<Language>> responseEntity = controller.getNameLanguageById(id);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }
}
