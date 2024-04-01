package undertaken.lab1.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class ParameterListTest {

    @Test
    void getParameters_ShouldReturnEmptyListIfNotSet() {
        // Arrange
        ParameterList parameterList = new ParameterList();
        parameterList.setParameters(Collections.emptyList()); // Устанавливаем список как пустой

        // Act
        List<String> parameters = parameterList.getParameters();

        // Assert
        assertNotNull(parameters, "List should not be null");
        assertTrue(parameters.isEmpty(), "List should be empty");
    }

    @Test
    void setParameters_ShouldSetParametersCorrectly() {
        // Arrange
        ParameterList parameterList = new ParameterList();
        List<String> expectedParameters = new ArrayList<>();
        expectedParameters.add("param1");
        expectedParameters.add("param2");

        // Act
        parameterList.setParameters(expectedParameters);
        List<String> actualParameters = parameterList.getParameters();

        // Assert
        assertNotNull(actualParameters);
        assertEquals(expectedParameters.size(), actualParameters.size());
        assertTrue(actualParameters.containsAll(expectedParameters));
    }
}
