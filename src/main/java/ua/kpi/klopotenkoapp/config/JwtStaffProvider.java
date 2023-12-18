package ua.kpi.klopotenkoapp.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import ua.kpi.klopotenkoapp.entity.StaffEntity;
import ua.kpi.klopotenkoapp.repository.StaffRepository;

import java.util.Collections;

public class JwtStaffProvider implements AuthenticationProvider {

    private final StaffRepository staffRepository;
    private final PasswordEncoder passwordEncoder;

    public JwtStaffProvider(StaffRepository staffRepository, PasswordEncoder passwordEncoder) {
        this.staffRepository = staffRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        String username = auth.getName();
        String password = auth.getCredentials().toString();
        StaffEntity staff = staffRepository.findByUsername(username).orElse(null);
        if (staff == null || !passwordEncoder.matches(password, staff.getPassword())) {
            throw new BadCredentialsException("External system authentication failed");
        }
        return new UsernamePasswordAuthenticationToken(username, staff.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(staff.getAccessLevel().toString())));
    }

    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }
}
