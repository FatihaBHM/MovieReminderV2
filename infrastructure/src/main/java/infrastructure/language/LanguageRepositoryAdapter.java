package infrastructure.language;

import org.lafabrique_epita.domain.repositories.LanguageRepository;
import org.springframework.stereotype.Repository;

@Repository
public class LanguageRepositoryAdapter implements LanguageRepository {

        private final LanguageJPARepository languageJPARepository;

        public LanguageRepositoryAdapter(LanguageJPARepository languageJPARepository) {
            this.languageJPARepository = languageJPARepository;
        }
}
