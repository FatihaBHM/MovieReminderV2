package org.lafabrique_epita.application.service.user;

import org.lafabrique_epita.application.dto.authentication.RegisterDto;
import org.lafabrique_epita.domain.exceptions.UserException;

public interface IUserService {

    // Long car c'est le type de l'id
    Long save(RegisterDto registerDto) throws UserException;
}
