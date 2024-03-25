package org.lafabrique_epita.application.service.media.serie;

import org.lafabrique_epita.domain.entities.MovieEntity;
import org.lafabrique_epita.domain.entities.SerieEntity;

import java.io.Serializable;

public interface ISerieService {
    SerieEntity save(SerieEntity serie);
}
