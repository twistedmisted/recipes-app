package ua.kpi.klopotenkoapp.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import ua.kpi.klopotenkoapp.entity.UserEntity;
import ua.kpi.klopotenkoapp.repository.UserRepository;

import java.util.Collections;

public class JwtUserProvider implements AuthenticationProvider {

    public static final String USER = "USER";
    public static final String ROLE_USER = "ROLE_" + USER;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public JwtUserProvider(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        String email = auth.getName();
        String password = auth.getCredentials().toString();
        UserEntity user = userRepository.findByEmail(email).orElse(null);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("External system authentication failed");
        }
        return new UsernamePasswordAuthenticationToken(email, user.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(ROLE_USER)));
    }

    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }
}
