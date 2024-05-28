package com.abs.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.abs.auth.AuthenticationRequest;
import com.abs.auth.AuthenticationResponse;
import com.abs.auth.RegisterResponse;
import com.abs.model.User;
import com.abs.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private RegisterResponse registerResponse;

    @InjectMocks
    private AuthService authService;

    
    @Test
    void testAuthenticate_ValidCredentials() {
       
        User user = new User();
        user.setEmail("divya@gmail.com");
        user.setPassword("encodedPassword"); 
        when(userRepository.findByEmail("divya@gmail.com")).thenReturn(Optional.of(user));

        when(jwtService.generateToken(any(User.class))).thenReturn("jwtToken");

        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("divya@gmail.com");
        request.setPassword("password");

        AuthenticationResponse response = authService.authenticate(request);

        assertNotNull(response);
        assertEquals("jwtToken", response.getAccessToken());
    }

    @Test
    void testAuthenticate_InvalidCredentials() {
      
        when(userRepository.findByEmail("divya@gmail.com")).thenReturn(Optional.empty());

        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("divya@gmail.com");
        request.setPassword("invalidPassword");

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.authenticate(request);
        });

        assertEquals("User not found", exception.getMessage());
    }
}

