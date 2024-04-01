package undertaken.lab1.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import undertaken.lab1.entity.Language;
import undertaken.lab1.entity.Text;
import undertaken.lab1.repository.LanguageRepository;
import undertaken.lab1.repository.TextRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UsefulTextLanguageTest {

    @Mock
    private TextRepository textRepository;

    @Mock
    private LanguageRepository languageRepository;

    @InjectMocks
    private UsefulTextLanguage usefulTextLanguage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllTexts() {
        List<Text> texts = new ArrayList<>();
        when(textRepository.findAll()).thenReturn(texts);

        List<Text> result = usefulTextLanguage.getAllTexts();

        assertEquals(texts, result);
    }

    @Test
    void getAllLanguage() {
        List<Language> languages = new ArrayList<>();
        when(languageRepository.findAll()).thenReturn(languages);

        List<Language> result = usefulTextLanguage.getAllLanguage();

        assertEquals(languages, result);
    }

    @Test
    void getAllTextByLanguage() {
        String languageName = "English";
        List<Text> expectedTexts = new ArrayList<>();

        when(textRepository.findAllByTextLanguageName(languageName)).thenReturn(expectedTexts);

        List<Text> result = usefulTextLanguage.getAllTextByLanguage(languageName);

        assertEquals(expectedTexts, result);
    }

    @Test
    void getTextById() {
        Long id = 1L;
        Text expectedText = new Text();
        when(textRepository.findById(id)).thenReturn(Optional.of(expectedText));

        Optional<Text> result = usefulTextLanguage.getTextById(id);
        assertEquals(Optional.of(expectedText), result);
    }

    @Test
    void getLanguageById() {
        Long id = 1L;
        Language expectedLanguage = new Language();
        when(languageRepository.findById(id)).thenReturn(Optional.of(expectedLanguage));

        Optional<Language> result = usefulTextLanguage.getLanguageById(id);
        assertEquals(Optional.of(expectedLanguage), result);
    }
}