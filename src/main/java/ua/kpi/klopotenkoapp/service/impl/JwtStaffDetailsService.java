package ua.kpi.klopotenkoapp.service.impl;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.kpi.klopotenkoapp.entity.StaffEntity;
import ua.kpi.klopotenkoapp.repository.StaffRepository;

import java.util.Collections;

@Service
public class JwtStaffDetailsService implements UserDetailsService {

    private final StaffRepository staffRepository;

    public JwtStaffDetailsService(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final StaffEntity staff = staffRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));
        return new User(username, staff.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(staff.getAccessLevel().toString())));
    }
}
