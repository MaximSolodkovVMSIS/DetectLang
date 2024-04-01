package undertaken.lab1.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import undertaken.lab1.entity.Language;
import undertaken.lab1.repository.LanguageRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class NameLanguageServiceTest {

    @Mock
    private LanguageRepository languageRepository;

    @InjectMocks
    private NameLanguageService nameLanguageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this); //инициализирование макетов перед каждым тестом
    }

    @Test
    void findByName_LanguageFound() {
        String languageName = "English";
        Language language = new Language();
        language.setName(languageName);
        when(languageRepository.findByName(languageName)).thenReturn(language);

        Language result = nameLanguageService.findByName(languageName);

        assertEquals(language, result);
        verify(languageRepository, times(1)).findByName(languageName);
    }

    @Test
    void findByName_LanguageNotFound() {
        String languageName = "UnknownLanguage";
        when(languageRepository.findByName(languageName)).thenReturn(null);

        Language result = nameLanguageService.findByName(languageName);

        assertNull(result);
        verify(languageRepository, times(1)).findByName(languageName);

    }

    @Test
    void save_LanguageSavedSuccessfully() {
        String languageName = "English";
        Language language = new Language();
        language.setName(languageName);
        when(languageRepository.save(any(Language.class))).thenReturn(language);

        Language result = nameLanguageService.save(languageName);

        assertEquals(language, result);
        verify(languageRepository, times(1)).save(any(Language.class));
    }
}