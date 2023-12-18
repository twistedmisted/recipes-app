package ua.kpi.klopotenkoapp.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ua.kpi.klopotenkoapp.repository.StaffRepository;
import ua.kpi.klopotenkoapp.repository.UserRepository;
import ua.kpi.klopotenkoapp.web.rest.api.v1.filter.CORSFilter;
import ua.kpi.klopotenkoapp.web.rest.api.v1.filter.JwtRequestFilter;
import ua.kpi.klopotenkoapp.web.rest.api.v1.filter.JwtStaffRequestFilter;

import java.util.Arrays;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static ua.kpi.klopotenkoapp.config.JwtUserProvider.ROLE_USER;
import static ua.kpi.klopotenkoapp.config.JwtUserProvider.USER;
import static ua.kpi.klopotenkoapp.contract.util.StaffAccessLevel.ADMIN;
import static ua.kpi.klopotenkoapp.contract.util.StaffAccessLevel.SUPPORT;

@Configuration
@RequiredArgsConstructor
public class JwtSecurityConfig {

    private static final String API_PATH = "/api/v1";

    private final JwtRequestFilter jwtRequestFilter;
    private final JwtStaffRequestFilter jwtStaffRequestFilter;
    private final UserRepository userRepository;
    private final StaffRepository staffRepository;
    private final CORSFilter corsFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtUserProvider jwtUserProvider() {
        return new JwtUserProvider(userRepository, passwordEncoder());
    }

    @Bean
    public JwtStaffProvider jwtStaffProvider() {
        return new JwtStaffProvider(staffRepository, passwordEncoder());
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(Arrays.asList(jwtUserProvider(), jwtStaffProvider()));
    }

    @Bean
    public SecurityFilterChain configure(final HttpSecurity http) throws Exception {
        return http.cors().and()
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(POST, API_PATH + "/auth/**").permitAll()
                .requestMatchers(GET, API_PATH + "/recipes/{id}", API_PATH + "/recipes", API_PATH + "/categories", API_PATH + "/regions").permitAll()
                .requestMatchers(POST, API_PATH + "/recipes").hasRole(USER)
                .anyRequest().hasAnyAuthority(ROLE_USER, ADMIN.name(), SUPPORT.name()).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .addFilterBefore(corsFilter, ChannelProcessingFilter.class)
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
