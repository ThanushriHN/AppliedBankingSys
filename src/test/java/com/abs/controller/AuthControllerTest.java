package com.abs.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.abs.auth.AuthenticationRequest;
import com.abs.auth.AuthenticationResponse;
import com.abs.auth.RegisterRequest;
import com.abs.auth.RegisterResponse;
import com.abs.model.Role;
import com.abs.service.AuthService;

class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister() {
        RegisterRequest registerRequest = RegisterRequest.builder()
                .firstName("kavya")
                .lastName("gowda")
                .email("kavya@gmail.com")
                .password("123456")
                .role(Role.MANAGER)
                .build();
                
        RegisterResponse registerResponse = RegisterResponse.builder()
                .id(1)
                .email("kavya@gmail.com")
                .password("123456")
                .build();

        when(authService.register(registerRequest)).thenReturn(registerResponse);

        ResponseEntity<RegisterResponse> response = authController.register(registerRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(registerResponse, response.getBody());
        verify(authService, times(1)).register(registerRequest);
    }

    @Test
    void testAuthenticate() {
        AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
                .email("divya@gmail.com")
                .password("test")
                .build();

        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                .accessToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkaXZ5YUBnbWFpbC5jb20iLCJhdXRob3JpdGllcyI6InJlYWQsdXBkYXRlLGNyZWF0ZSxST0xFX01BTkFHRVIsZGVsZXRlIiwiaWF0IjoxNzE2MzU1NTczLCJleHAiOjE3MTY3MDExNzN9.xSpzPIibRvo6Egt1SzkzxaXlLlocGz4pkm6Tz8lUuRE")
                .build();

        when(authService.authenticate(authenticationRequest)).thenReturn(authenticationResponse);

        ResponseEntity<AuthenticationResponse> response = authController.authenticate(authenticationRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(authenticationResponse, response.getBody());
        verify(authService, times(1)).authenticate(authenticationRequest);
    }
}
