package ua.kpi.klopotenkoapp.web.rest.api.v1.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ua.kpi.klopotenkoapp.contract.bo.AuthenticationRequest;
import ua.kpi.klopotenkoapp.contract.bo.AuthenticationResponse;
import ua.kpi.klopotenkoapp.contract.bo.RegistrationRequest;
import ua.kpi.klopotenkoapp.contract.util.StaffAccessLevel;
import ua.kpi.klopotenkoapp.entity.StaffEntity;
import ua.kpi.klopotenkoapp.repository.StaffRepository;
import ua.kpi.klopotenkoapp.service.impl.JwtTokenService;
import ua.kpi.klopotenkoapp.service.impl.RegistrationService;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final RegistrationService registrationService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticateUser(@RequestBody @Valid final AuthenticationRequest authenticationRequest) {
        Authentication authenticate;
        try {
            authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getLogin(), authenticationRequest.getPassword()));
        } catch (final BadCredentialsException ex) {
            throw new ResponseStatusException(UNAUTHORIZED, "Не правильно введено пошту та пароль, первірте правильність вводу.");
        }
        final AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setAccessToken(jwtTokenService.generateToken(authenticate));
        return new ResponseEntity<>(authenticationResponse, OK);
    }

//    @PostMapping("/staff/login")
//    public ResponseEntity<AuthenticationResponse> authenticateStaff(@RequestBody @Valid final AuthenticationRequest authenticationRequest) {
//        Authentication authenticate;
//        try {
//            authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                    authenticationRequest.getLogin(), authenticationRequest.getPassword()));
//        } catch (final BadCredentialsException ex) {
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
//        }
//        final AuthenticationResponse authenticationResponse = new AuthenticationResponse();
//        authenticationResponse.setAccessToken(jwtTokenService.generateToken(authenticate));
//        return new ResponseEntity<>(authenticationResponse, OK);
//    }

    private final StaffRepository staffRepository;

    @GetMapping("/staff/register")
    public String regS() {
        StaffEntity staff = new StaffEntity();
        staff.setName("Name");
        staff.setSurname("Surname");
        staff.setUsername("username");
        BCryptPasswordEncoder b = new BCryptPasswordEncoder();
        staff.setPassword(b.encode("password123"));
        staff.setAccessLevel(StaffAccessLevel.ADMIN);
        staffRepository.save(staff);
        return "OK";
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegistrationRequest user) {
        log.debug("Registration user with username = [{}]", user.getUsername());
        registrationService.registerUser(user);
        return new ResponseEntity<>(CREATED);
    }
}
