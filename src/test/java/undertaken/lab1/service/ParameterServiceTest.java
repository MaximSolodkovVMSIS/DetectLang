package undertaken.lab1.service;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import undertaken.lab1.dto.ParameterList;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ParameterServiceTest {

    @Test
    void processParameters_ShouldConvertParametersToUpperCase() {
        ParameterList parameterList = new ParameterList();
        List<String> parameters = Arrays.asList("param1", "param2", "param3");
        parameterList.setParameters(parameters);

        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));

        ParameterService parameterService = new ParameterService();

        // Act
        parameterService.processParameters(parameterList);

        // Assert
        String[] expectedOutput = {"PARAM1", "PARAM2", "PARAM3"};
        String[] actualOutput = outputStreamCaptor.toString().trim().split(System.lineSeparator());
        assertEquals(expectedOutput.length, actualOutput.length);
        for (int i = 0; i < expectedOutput.length; i++) {
            assertEquals(expectedOutput[i], actualOutput[i]);
        }
    }
}