package ua.kpi.klopotenkoapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.kpi.klopotenkoapp.contract.dto.UserDTO;
import ua.kpi.klopotenkoapp.mapper.impl.UserMapper;
import ua.kpi.klopotenkoapp.repository.UserRepository;
import ua.kpi.klopotenkoapp.service.UserService;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDTO getUserByEmail(String email) {
        log.debug("Getting user by email = [{}]", email);
        return userMapper.entityToDto(userRepository.findByEmail(email).orElse(null));
    }

    @Override
    public String getUserEmailByRecipeId(long recipeId) {
        log.debug("Getting user email by recipe id = [{}]", recipeId);
        String email = userRepository.findUserEmailByRecipeId(recipeId);
        if (isNull(email) || email.isBlank()) {
            log.warn("Cannot get user email by recipe id");
            return null;
        }
        return email;
    }
}
