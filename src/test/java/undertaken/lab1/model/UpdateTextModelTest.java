package undertaken.lab1.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UpdateTextModelTest {

    @Test
    void getId() {
        UpdateTextModel updateTextModel = new UpdateTextModel();
        Long expectedId = 123L;
        updateTextModel.setId(expectedId);
        assertEquals(expectedId, updateTextModel.getId());
    }

    @Test
    void getNewText() {
        UpdateTextModel updateTextModel = new UpdateTextModel();
        String expectedNewText = "New Text";
        updateTextModel.setNewText(expectedNewText);
        assertEquals(expectedNewText, updateTextModel.getNewText());
    }
}