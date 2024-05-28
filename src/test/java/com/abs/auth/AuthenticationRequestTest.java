package com.abs.auth;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class AuthenticationRequestTest {

    @Test
    void testNoArgsConstructor() {
        AuthenticationRequest request = new AuthenticationRequest();
        assertNull(request.getEmail());
        assertNull(request.getPassword());
    }

    @Test
    void testAllArgsConstructor() {
        AuthenticationRequest request = new AuthenticationRequest("divya@gmail.com", "test");
        assertEquals("divya@gmail.com", request.getEmail());
        assertEquals("test", request.getPassword());
    }

    @Test
    void testBuilder() {
        AuthenticationRequest request = AuthenticationRequest.builder()
                .email("divya@gmail.com")
                .password("test")
                .build();
        assertEquals("divya@gmail.com", request.getEmail());
        assertEquals("test", request.getPassword());
    }

    @Test
    void testSettersAndGetters() {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("divya@gmail.com");
        request.setPassword("test");
        assertEquals("divya@gmail.com", request.getEmail());
        assertEquals("test", request.getPassword());
    }

    @Test
    void testToString() {
        AuthenticationRequest request = new AuthenticationRequest("divya@gmail.com", "test");
        String expected = "AuthenticationRequest(email=divya@gmail.com, password=test)";
        assertEquals(expected, request.toString());
    }
}
