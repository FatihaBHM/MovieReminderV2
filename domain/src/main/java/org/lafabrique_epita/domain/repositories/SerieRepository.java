package org.lafabrique_epita.domain.repositories;

import org.lafabrique_epita.domain.entities.MovieEntity;
import org.lafabrique_epita.domain.entities.SerieEntity;

public interface SerieRepository {

    SerieEntity save(SerieEntity serie);
}
