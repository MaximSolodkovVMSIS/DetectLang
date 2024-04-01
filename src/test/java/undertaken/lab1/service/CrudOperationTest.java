package undertaken.lab1.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import undertaken.lab1.entity.Text;
import undertaken.lab1.repository.LanguageRepository;
import undertaken.lab1.repository.TextRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class CrudOperationTest {
    @Mock
    private TextRepository textRepository;

    @InjectMocks
    private CrudOperation crudOperation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void addTextAndDetectLanguage() {
        ServiceApiKey serviceApiKey = mock(ServiceApiKey.class);
        LanguageDetectiveService languageDetectiveService = mock(LanguageDetectiveService.class);
        NameLanguageService nameLanguageService = mock(NameLanguageService.class);
        TextLanguageService textLanguageService = mock(TextLanguageService.class);
        TextRepository textRepository = mock(TextRepository.class);
        LanguageRepository languageRepository = mock(LanguageRepository.class);

        // Установка поведения мок-объектов
        String apiKey = "testApiKey";
        when(serviceApiKey.getApiKey()).thenReturn(apiKey);

        String detectedLanguage = "English";
        when(languageDetectiveService.detectLanguage(any(), eq(apiKey))).thenReturn(detectedLanguage);

        // Создание экземпляра класса, который тестируем
        CrudOperation crudOperation = new CrudOperation(textLanguageService, languageDetectiveService, nameLanguageService, serviceApiKey, textRepository, languageRepository);

        // Вызов тестируемого метода
        String text = "Test text";
        String result = crudOperation.addTextAndDetectLanguage(text);

        // Проверка ожидаемого результата
        String expectedResult = "Text and language saved successfully";
        assertEquals(expectedResult, result);

        // Проверка вызовов методов зависимостей
        verify(serviceApiKey, times(1)).getApiKey();
        verify(languageDetectiveService, times(1)).detectLanguage(any(), eq(apiKey));
        verify(nameLanguageService, times(1)).findByName(detectedLanguage);
        verify(nameLanguageService, times(1)).save(detectedLanguage);
        verify(textLanguageService, times(1)).save(any());
    }

    @Test
    void deleteTextFoundAndDeletedSuccessfully()  {
        String textContent = "Hello";
        Text text = new Text();
        text.setText(textContent);
        when(textRepository.findByText(textContent)).thenReturn(text);
        // Act
        String result = crudOperation.deleteText(textContent);
        // Assert
        assertEquals("Text deleted successfully", result);
        verify(textRepository, times(1)).delete(text);
    }

    @Test
    void deleteText_TextNotFound() {
        String textContent = "UnknownText";
        when(textRepository.findByText(textContent)).thenReturn(null);

        String result = crudOperation.deleteText(textContent);
        assertEquals("Text not found", result);
        verify(textRepository, never()).delete(any(Text.class));
    }
    @Test
    void deleteLanguageAndText() {
    }

    @Test
    void updateText() {
    }
}