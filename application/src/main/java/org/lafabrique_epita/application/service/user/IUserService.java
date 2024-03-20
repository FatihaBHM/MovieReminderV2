package org.lafabrique_epita.application.service.user;

import org.lafabrique_epita.domain.entities.UserEntity;

public interface IUserService {

    // Long car c'est le type de l'id
    Long save(UserEntity user);
}
