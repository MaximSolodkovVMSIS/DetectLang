package undertaken.lab1.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import undertaken.lab1.entity.Text;
import undertaken.lab1.repository.LanguageRepository;
import undertaken.lab1.repository.TextRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class CrudOperationTest {
    @Mock
    private TextRepository textRepository;

    @InjectMocks
    private CrudOperation crudOperation;

    @Test
    public void testDeleteText() {
        // Создаем тестовую строку текста
        String text = "Test text";
        Text textLanguage = new Text();
        // Устанавливаем поведение мок-объекта textRepository
        when(textRepository.findByText(text)).thenReturn(textLanguage);

        // Вызываем метод, который будем тестировать
        String result = crudOperation.deleteText(text);

        // Проверяем ожидаемый результат
        assertEquals("Text deleted successfully", result);

        // Проверяем, что метод findByText был вызван один раз с параметром text
        verify(textRepository, times(1)).findByText(text);

        // Проверяем, что метод delete был вызван один раз с объектом text
        verify(textRepository, times(1)).delete(textLanguage);
    }

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

    @Test
    public void testUpdateText() {
        // Инициализация Mockito

        // Создание тестовых данных
        Long id = 1L;
        String newText = "Updated text";

        // Создание мок объекта Text
        Text text = new Text();
        text.setId(id);
        text.setText("Initial text");

        // Устанавливаем поведение мок-объекта textRepository
        when(textRepository.findById(id)).thenReturn(Optional.of(text));
        when(textRepository.save(text)).thenReturn(text);

        // Вызов метода, который будем тестировать
        String result = crudOperation.updateText(id, newText);

        // Проверка ожидаемого результата
        assertEquals("Text updated successfully", result);

        // Проверка, что метод findById был вызван один раз с параметром id
        verify(textRepository, times(1)).findById(id);

        // Проверка, что метод save был вызван один раз с объектом text
        verify(textRepository, times(1)).save(text);

        // Проверка, что текст был обновлен корректно
        assertEquals(newText, text.getText());
    }
}