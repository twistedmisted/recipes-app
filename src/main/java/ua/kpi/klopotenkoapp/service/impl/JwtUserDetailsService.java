package ua.kpi.klopotenkoapp.service.impl;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.kpi.klopotenkoapp.entity.UserEntity;
import ua.kpi.klopotenkoapp.repository.UserRepository;

import java.util.Collections;

import static ua.kpi.klopotenkoapp.config.JwtUserProvider.ROLE_USER;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User " + email + " not found"));
        return new User(email, user.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(ROLE_USER)));
    }
}
