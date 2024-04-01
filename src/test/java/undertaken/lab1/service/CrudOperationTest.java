package undertaken.lab1.service;

import org.junit.jupiter.api.Test;
import undertaken.lab1.repository.LanguageRepository;
import undertaken.lab1.repository.TextRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class CrudOperationTest {
    @Test
    public void testAddTextAndDetectLanguage() {
        // Создание мок-объектов зависимостей
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
}