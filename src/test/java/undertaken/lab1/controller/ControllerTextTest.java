package undertaken.lab1.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import undertaken.lab1.entity.Text;
import undertaken.lab1.service.UsefulTextLanguage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

class ControllerTextTest {

    @Mock
    private UsefulTextLanguage usefulTextLanguage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void getTextByLanguage() {
        // Arrange
        String languageName = "English";
        List<Text> expectedTexts = new ArrayList<>(); // Предположим, что у нас есть список текстов, связанных с языком

        when(usefulTextLanguage.getAllTextByLanguage(languageName)).thenReturn(expectedTexts);

        ControllerText controller = new ControllerText(usefulTextLanguage);

        List<Text> actualTexts = controller.getTextByLanguage(languageName);

        assertEquals(expectedTexts, actualTexts);
    }

    @Test
    void getAllTexts() {
        List<Text> expectedTexts = new ArrayList<>(); // Предположим, что у нас есть список всех текстов

        when(usefulTextLanguage.getAllTexts()).thenReturn(expectedTexts);

        ControllerText controller = new ControllerText(usefulTextLanguage);

        List<Text> actualTexts = controller.getAllTexts();

        assertEquals(expectedTexts, actualTexts);
    }

    @Test
    void getTextById_TextFound() {
        Long id = 1L;
        Text expectedText = new Text(); // Предположим, что мы нашли текст с заданным идентификатором

        when(usefulTextLanguage.getTextById(id)).thenReturn(Optional.of(expectedText));

        ControllerText controller = new ControllerText(usefulTextLanguage);

        ResponseEntity<Optional<Text>> responseEntity = controller.getTextById(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedText, responseEntity.getBody().orElse(null));
    }

    @Test
    void getTextById_TextNotFound() {
        Long id = 1L;

        when(usefulTextLanguage.getTextById(id)).thenReturn(Optional.empty()); // Предположим, что текст не найден

        ControllerText controller = new ControllerText(usefulTextLanguage);

        ResponseEntity<Optional<Text>> responseEntity = controller.getTextById(id);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }
}
