package undertaken.lab1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import undertaken.lab1.dto.ParameterList;

import java.util.List;

@Service
public class ParameterService {

    private static final Logger logger = LoggerFactory.getLogger(ParameterService.class);

    public void processParameters(ParameterList parameterList) {
        List<String> parameters = parameterList.getParameters();
        // Пример обработки параметров с использованием Java 8 Stream API
        parameters.stream()
                .map(String::toUpperCase)
                .forEach(logger::info);
    }
}
