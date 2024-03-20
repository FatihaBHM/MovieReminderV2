package infrastructure.favorite;

import org.lafabrique_epita.domain.repositories.FavoriteRepository;
import org.springframework.stereotype.Repository;

@Repository
public class FavoriteRepositoryAdapter implements FavoriteRepository {

    private final FavoriteJPARepository favoriteJPARepository;

    public FavoriteRepositoryAdapter(FavoriteJPARepository favoriteJPARepository) {
        this.favoriteJPARepository = favoriteJPARepository;
    }
}
