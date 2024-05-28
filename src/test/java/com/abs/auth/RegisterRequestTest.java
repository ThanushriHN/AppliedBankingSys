package com.abs.auth;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RegisterRequestTest {

	@Test
    void testNoArgsConstructor() {
        RegisterRequest request = new RegisterRequest();
        assertNull(request.getEmail());
        assertNull(request.getFirstName());
        assertNull(request.getLastName());
        assertNull(request.getPassword());
        assertNull(request.getRole());
        
        
    }

}
