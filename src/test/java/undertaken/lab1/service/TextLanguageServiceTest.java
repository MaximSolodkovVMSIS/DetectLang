package undertaken.lab1.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import undertaken.lab1.entity.Text;
import undertaken.lab1.repository.TextRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

class TextLanguageServiceTest {

    @Test
    void save() {
        TextRepository textRepositoryMock = Mockito.mock(TextRepository.class);
        TextLanguageService textLanguageService = new TextLanguageService(textRepositoryMock);
        Text text = new Text(); // Создаем фиктивный объект Text

        textLanguageService.save(text);

        verify(textRepositoryMock).save(text); // Проверяем, что метод save был вызван с текстом
    }
}