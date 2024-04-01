package undertaken.lab1.exception;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


class EndpointActionLoggerTest {


    @Test
    void logAddTextAction() {
        Logger logger = mock(Logger.class);
        EndpointActionLogger actionLogger = new EndpointActionLogger();

        actionLogger.setLogger(logger);

        actionLogger.logAddTextAction();

        verify(logger).info("Adding text: ");
    }

    @Test
    void logDeleteTextAction() {
        Logger logger = mock(Logger.class);
        EndpointActionLogger actionLogger = new EndpointActionLogger();
        actionLogger.setLogger(logger);
        String text = "Example Text";

        actionLogger.logDeleteTextAction(text);

        verify(logger).info("Deleting text: {}", text);
    }

    @Test
    void logDeleteLanguageAction() {
        Logger logger = mock(Logger.class);
        EndpointActionLogger actionLogger = new EndpointActionLogger();
        actionLogger.setLogger(logger);
        String language = "English";

        actionLogger.logDeleteLanguageAction(language);

        verify(logger).info("Deleting language: {}", language);
    }
}