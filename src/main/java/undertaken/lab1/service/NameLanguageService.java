package undertaken.lab1.service;

import org.springframework.stereotype.Service;
import undertaken.lab1.entity.Language;
import undertaken.lab1.repository.LanguageRepository;


@Service
public class NameLanguageService {
    private final LanguageRepository languageRepository;

    public NameLanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    public Language findByName(String name) {
        return languageRepository.findByName(name);
    }

    public Language save(String name) {
        Language language = new Language();
        language.setName(name);
        return languageRepository.save(language);
    }
}
