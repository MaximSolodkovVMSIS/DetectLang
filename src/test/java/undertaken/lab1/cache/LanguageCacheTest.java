package undertaken.lab1.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;

import static org.junit.jupiter.api.Assertions.*;

class LanguageCacheTest {

    @Mock
    private Logger logger;

    private LanguageCache languageCache;

    @BeforeEach
    void setUp() {
        logger = Mockito.mock(Logger.class);
        languageCache = new LanguageCache();
    }

    @Test
    void get() {
        // Given
        String text = "Test Text";
        String language = "English";
        languageCache.put(text, language);
        // When
        String result = languageCache.get(text);
        // Then
        assertEquals(language, result);
    }

    @Test
    void put() {
        String text = "Test Text";
        String language = "English";
        languageCache.put(text, language);
        assertEquals(language, languageCache.get(text));
    }
}