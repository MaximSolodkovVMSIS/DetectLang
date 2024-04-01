package undertaken.lab1.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import undertaken.lab1.entity.Language;
import undertaken.lab1.service.UsefulTextLanguage;

@RestController
@RequestMapping("/languages")
public class ControllerLanguage {
    private final UsefulTextLanguage usefulTextLanguage;

    public ControllerLanguage(UsefulTextLanguage usefulTextLanguage) {
        this.usefulTextLanguage = usefulTextLanguage;
    }

    @GetMapping("/all")
    public List<Language> getAllLanguages() {
        return usefulTextLanguage.getAllLanguage();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Language>> getNameLanguageById(@PathVariable Long id) {
        Optional<Language> nameLanguage = usefulTextLanguage.getLanguageById(id);
        if (nameLanguage.isPresent()) {
            return ResponseEntity.ok().body(nameLanguage);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
