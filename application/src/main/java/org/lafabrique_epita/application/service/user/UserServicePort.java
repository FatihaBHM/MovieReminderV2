package org.lafabrique_epita.application.service.user;

import org.lafabrique_epita.domain.entities.UserEntity;
import org.lafabrique_epita.domain.exceptions.UserException;

public interface UserServicePort {

    // Long car c'est le type de l'id
    Long save(UserEntity user) throws UserException;

    boolean findByEmail(String email);
}
