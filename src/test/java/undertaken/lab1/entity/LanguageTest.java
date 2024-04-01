package undertaken.lab1.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LanguageTest {

    @Test
    void testGetIdAndSetId() {
        Language language = new Language();
        Long expectedId = 123L;
        language.setId(expectedId);
        assertEquals(expectedId, language.getId());
    }
}