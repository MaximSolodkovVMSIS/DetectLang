package undertaken.lab1.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import undertaken.lab1.cache.LanguageCache;
import undertaken.lab1.dto.LanguageDetectionRequest;
import undertaken.lab1.entity.Language;
import undertaken.lab1.entity.Text;
import undertaken.lab1.exception.EndpointActionLogger;
import undertaken.lab1.model.UpdateTextModel;
import undertaken.lab1.service.CrudOperation;
import undertaken.lab1.service.LanguageDetectiveService;
import undertaken.lab1.service.NameLanguageService;
import undertaken.lab1.service.ServiceApiKey;
import undertaken.lab1.service.TextLanguageService;


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
    public String detectLanguage(@RequestParam String text) {
        String cachedLanguage = cache.get(text);
        endpointActionLogger.logAddTextAction(text);
        endpointActionLogger.logAddTextAction(text);
        if (cachedLanguage != null) {
            return cachedLanguage;
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

        return "Text and language saved successfully";
    }

    @PostMapping("/api/v1/text")
    public String addText(@RequestBody Map<String, String> requestBody) {
        String text = requestBody.get("text").replace("\\+", " ");
        endpointActionLogger.logAddTextAction(text);
        endpointActionLogger.logAddTextAction(text);
        return crudOperation.addTextAndDetectLanguage(text);
    }

    @DeleteMapping("/api/v1/text")//пара ключ значений Map
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
    public String updateText(@PathVariable Long id, @RequestBody UpdateTextModel updateTextModel) {
        return crudOperation.updateText(id, updateTextModel.getNewText());
    }

    @GetMapping("/cache")
    public void printCache() {
        cache.printCache();
    }
}
