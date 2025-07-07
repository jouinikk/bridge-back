package com.example.cars.services;

import com.example.cars.config.JwtService;
import com.example.cars.entities.*;
import com.example.cars.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    public AuthenticationResponse register(RegisterRequest request) {

        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .role(Role.ADMIN)
                .password(passwordEncoder.encode(request.getPassword()))
                .anneeExperience(request.getAnneeExperience())
                .specialite(request.getSpecialite())
                .telephone(request.getTelephone())
                .niveau(request.getNiveau())
                .build()
                ;
        user = repository.save(user);
        userService.addUser(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public ResponseCookie authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        var user = repository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);

        // Set the JWT as a secure, HttpOnly cookie
        ResponseCookie cookie = ResponseCookie.from("jwt", jwtToken)
                .httpOnly(false) // Set to false to make it accessible via JavaScript for debugging
                .secure(false) // ⚠️ Set to true in production (with HTTPS)
                .path("/")
                .maxAge(24 * 60 * 60)
                .sameSite("Lax")
                .build();
        return cookie;
    }


}
