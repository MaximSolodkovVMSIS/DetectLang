package undertaken.lab1.entity;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class LanguageTest {

    @Test
    void testSetId() {
        Language language = new Language();
        Long expectedId = 1L;
        language.setId(expectedId);
        assertEquals(expectedId, language.getId());
    }

    @Test
    void testGetName() {
        Language language = new Language();
        String expectedName = "English";
        language.setName(expectedName);
        assertEquals(expectedName, language.getName());
    }

    @Test
    void testSetName() {
        Language language = new Language();
        String expectedName = "English";
        language.setName(expectedName);
        assertEquals(expectedName, language.getName());
    }

    @Test
    void testGetTextLanguages() {
        Language language = new Language();
        Text text1 = new Text();
        Text text2 = new Text();
        List<Text> expectedTextLanguages = new ArrayList<>();
        expectedTextLanguages.add(text1);
        expectedTextLanguages.add(text2);
        language.setTextLanguages(expectedTextLanguages);
        assertEquals(expectedTextLanguages, language.getTextLanguages());
    }

    @Test
    void testSetTextLanguages() {
        Language language = new Language();
        Text text1 = new Text();
        Text text2 = new Text();
        List<Text> expectedTextLanguages = new ArrayList<>();
        expectedTextLanguages.add(text1);
        expectedTextLanguages.add(text2);
        language.setTextLanguages(expectedTextLanguages);
        assertEquals(expectedTextLanguages, language.getTextLanguages());
    }
}