package undertaken.lab1.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import undertaken.lab1.entity.Language;
import undertaken.lab1.entity.Text;
import undertaken.lab1.repository.LanguageRepository;
import undertaken.lab1.repository.TextRepository;

@Service
public class UsefulTextLanguage {
    private final TextRepository textRepository;
    private final LanguageRepository languageRepository;

    public List <Text> getAllTexts() {
        return textRepository.findAll();
    }

    public List<Language> getAllLanguage() {
        return languageRepository.findAll();
    }

    public UsefulTextLanguage(TextRepository textRepository, LanguageRepository languageRepository) {
        this.textRepository = textRepository;
        this.languageRepository = languageRepository;
    }

    public List<Text> getAllTextByLanguage(String languageName) {
        return textRepository.findAllByTextLanguageName(languageName);
    }

    public Optional<Text> getTextById(Long id) {
        return textRepository.findById(id);
    }

    public Optional<Language> getLanguageById(Long id) {
        return languageRepository.findById(id);
    }
}
