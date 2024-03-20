package infrastructure.country;

import org.lafabrique_epita.domain.repositories.CountryRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CountryRepositoryAdapter implements CountryRepository {

    private final CountryJPARepository countryJPARepository;

    public CountryRepositoryAdapter(CountryJPARepository countryJPARepository) {
        this.countryJPARepository = countryJPARepository;
    }
}
