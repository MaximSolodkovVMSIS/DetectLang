package undertaken.lab1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import undertaken.lab1.entity.Language;

public interface LanguageRepository extends JpaRepository<Language, Long> { //тип идентификатора(id)
    Language findByName(String name);
}
