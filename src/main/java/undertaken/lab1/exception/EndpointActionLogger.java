package undertaken.lab1.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class EndpointActionLogger {
    private final Logger logger = LoggerFactory.getLogger(EndpointActionLogger.class);

    public void logAddTextAction(String text) {
        logger.info("Adding text: ");
    }

    public void logDeleteTextAction(String text) {
        logger.info("Deleting text: {}", text);
    }

    public void logDeleteLanguageAction(String language) {
        logger.info("Deleting language: {}", language);
    }
}
