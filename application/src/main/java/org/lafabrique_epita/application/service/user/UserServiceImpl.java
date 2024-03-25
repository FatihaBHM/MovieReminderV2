package org.lafabrique_epita.application.service.user;

import org.lafabrique_epita.application.dto.authentication.RegisterDto;
import org.lafabrique_epita.application.dto.authentication.RegisterDtoMapper;
import org.lafabrique_epita.domain.entities.UserEntity;
import org.lafabrique_epita.domain.exceptions.UserException;
import org.lafabrique_epita.domain.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements IUserService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Long save(RegisterDto registerDto) throws UserException {
        if (this.userRepository.findByEmail(registerDto.email()).isPresent()) {
            throw new UserException("Email already exists", HttpStatus.BAD_REQUEST);
        }

        UserEntity user = RegisterDtoMapper.convertToUserEntity(registerDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
