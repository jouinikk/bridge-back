package com.example.cars.restcontrollers;

import com.example.cars.Repositories.UserRepository;
import com.example.cars.config.JwtService;
import com.example.cars.entities.AuthenticationRequest;
import com.example.cars.entities.AuthenticationResponse;
import com.example.cars.services.AuthenticationService;
import com.example.cars.entities.RegisterRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
        ResponseCookie cookie = this.authenticationService.authenticate(request);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(Map.of("message", "Authenticated"));
    }

    @GetMapping("/me")
    public ResponseEntity<?> getUserInfo(HttpServletRequest request) {
        String token = null;

        for (Cookie cookie : request.getCookies()) {
            if ("jwt".equals(cookie.getName())) {
                token = cookie.getValue();
                break;
            }
        }

        if (token == null || !jwtService.isTokenValid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String username = jwtService.getUserEmail(token);
        var user = userRepository.findByEmail(username).orElseThrow();

        return ResponseEntity.ok(Map.of("role", user.getRole().name()));
    }
    @GetMapping("/connected")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }

        UserDetails user = (UserDetails) authentication.getPrincipal();

        // You can cast to your own User class if needed
        return ResponseEntity.ok(user);
    }

}
