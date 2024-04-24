package undertaken.lab1.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import undertaken.lab1.cache.LanguageCache;
import undertaken.lab1.dto.LanguageDetectionRequest;
import undertaken.lab1.dto.ParameterList;
import undertaken.lab1.entity.Language;
import undertaken.lab1.entity.Text;
import undertaken.lab1.exception.EndpointActionLogger;
import undertaken.lab1.model.UpdateTextModel;
import undertaken.lab1.service.*;


@RestController
public class ControllerRest {
    private final LanguageDetectiveService languageDetectiveService;
    private final ServiceApiKey serviceApiKey;
    private final NameLanguageService nameLanguageService;
    private final TextLanguageService textLanguageService;
    private final CrudOperation crudOperation;
    private final LanguageCache cache;
    private final EndpointActionLogger endpointActionLogger;

    @Autowired
    public ControllerRest(LanguageDetectiveService languageDetectiveService, ServiceApiKey serviceApiKey, NameLanguageService nameLanguageService, TextLanguageService textLanguageService, CrudOperation crudOperation, LanguageCache cache, EndpointActionLogger endpointActionLogger) {
        this.languageDetectiveService = languageDetectiveService;
        this.serviceApiKey = serviceApiKey;
        this.nameLanguageService = nameLanguageService;
        this.textLanguageService = textLanguageService;
        this.crudOperation = crudOperation;
        this.cache = cache;
        this.endpointActionLogger = endpointActionLogger;
    }

    @GetMapping("/api/v1/detect-language")
    @CrossOrigin(origins = "http://localhost:3000/")
    public Map<String, String> detectLanguage(@RequestParam String text) {
        RequestCounterService.incrementRequestCount();
        RequestCounterService.getRequestCount();
        String cachedLanguage = cache.get(text);
        if (cachedLanguage != null) {
            Map<String, String> result = new HashMap<>();
            result.put("message", "Text and language saved successfully");
            result.put("language", cachedLanguage);
            return result;
        }

        String apiKey = serviceApiKey.getApiKey();
        LanguageDetectionRequest request = new LanguageDetectionRequest(text);
        String detectedLanguage = languageDetectiveService.detectLanguage(request, apiKey);

        Language language = nameLanguageService.findByName(detectedLanguage);
        if (language == null) {
            language = nameLanguageService.save(detectedLanguage);
        }

        Text textLanguage = new Text();
        textLanguage.setText(text);
        textLanguage.setLanguage(language);
        textLanguageService.save(textLanguage);

        cache.put(text, detectedLanguage);

        Map<String, String> result = new HashMap<>();
        result.put("message", "Text and language saved successfully");
        result.put("language", detectedLanguage);
        return result;
    }


    @PostMapping("/api/v1/text/bulk")
    @CrossOrigin(origins = "http://localhost:3000/")
    public List<Map<String, String>> addTexts(@RequestBody ParameterList parameterList) {
        List<String> parameters = parameterList.getParameters();
        List<String> nonEmptyParameters = parameters.stream()
                .filter(text -> text != null && !text.trim().isEmpty()) // Отфильтровать пустые строки
                .collect(Collectors.toList());

        String apiKey = serviceApiKey.getApiKey();
        return nonEmptyParameters.stream()
                .map(text -> {
                    String detectedLanguage = languageDetectiveService.detectLanguage(new LanguageDetectionRequest(text), apiKey);
                    Language language = nameLanguageService.findByName(detectedLanguage);

                    if (language == null) {
                        language = nameLanguageService.save(detectedLanguage);
                    }

                    Text textLanguage = new Text();
                    textLanguage.setText(text);
                    textLanguage.setLanguage(language);
                    textLanguageService.save(textLanguage);

                    Map<String, String> result = new HashMap<>();
                    result.put("message", "Text and language saved successfully");
                    result.put("text", text);
                    result.put("language", detectedLanguage);

                    return result;
                })
                .collect(Collectors.toList());
    }



    @DeleteMapping("/api/v1/text")//пара ключ значений Map
    @CrossOrigin(origins = "http://localhost:3000/")
    public String deleteText(@RequestBody Map<String, String> requestBody) {
        String value = requestBody.get("value");
        endpointActionLogger.logDeleteTextAction(value);
        // Определите, что нужно удалить на основе типа и значения, например:
        return crudOperation.deleteText(value);
    }

    @DeleteMapping("/api/v1/language")
    public String deleteLanguage(@RequestBody Map<String, String> requestBody) {
        String value = requestBody.get("value");
        endpointActionLogger.logDeleteLanguageAction(value);
        return crudOperation.deleteLanguageAndText(value);
    }

    @PutMapping("/api/v1/text/{id}")
    @CrossOrigin(origins = "http://localhost:3000/")
    public String updateText(@PathVariable Long id, @RequestBody UpdateTextModel updateTextModel) {
        return crudOperation.updateText(id, updateTextModel.getNewText());
    }

    @GetMapping("/cache")
    public void printCache() {
        cache.printCache();
    }
}
