package ua.kpi.klopotenkoapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ua.kpi.klopotenkoapp.contract.bo.RegistrationRequest;
import ua.kpi.klopotenkoapp.entity.UserEntity;
import ua.kpi.klopotenkoapp.repository.UserRepository;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationService {

    private final UserRepository userRepository;

    public void registerUser(RegistrationRequest user) {
        if (existsUserByUsername(user.getUsername())) {
            throw new ResponseStatusException(BAD_REQUEST, "Дані ім'я користувача вже існує, спробуйте інше.");
        } else if (existsUserByEmail(user.getEmail())) {
            throw new ResponseStatusException(BAD_REQUEST, "Дана пошта вже існує, спробуйте іншу.");
        }
        UserEntity userToSave = parseToUserEntity(user);
        userRepository.save(userToSave);
    }

    private boolean existsUserByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    private boolean existsUserByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    private UserEntity parseToUserEntity(RegistrationRequest user) {
        UserEntity entity = new UserEntity();
        entity.setName(user.getName());
        entity.setSurname(user.getSurname());
        entity.setUsername(user.getUsername());
        entity.setEmail(user.getEmail());
        entity.setPassword(encryptPassword(user.getPassword()));
        entity.setVerified(false);
        return entity;
    }

    private String encryptPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
}
