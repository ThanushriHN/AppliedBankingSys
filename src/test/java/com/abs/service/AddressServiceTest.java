package com.abs.service;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;

import com.abs.dto.AddressDTO;
import com.abs.model.Address;
import com.abs.model.Customer;
import com.abs.model.User;
import com.abs.repository.AddressRepository;
import com.abs.repository.CustomerRepository;
import com.abs.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {
	
	@InjectMocks
	AddressService addressService;

	@Mock
	CustomerRepository customerRepository;
	
	 @Mock 
	 private JwtService jwtService;
	 
	 @Mock
	  private UserRepository userRepository;
	 
	 @Mock
	 private AddressRepository addressRepository;
	 
	 @BeforeEach
	    void setUp() {
	        MockitoAnnotations.openMocks(this);
	 }

	 /*
	  * Adding an address for a customer
	  * set up the necessary mocks and verifies that the addressService
      * can add a new address for a customer using the provided JWT token and request data.
	  */
	@Test
	void createAddressTest() {
		// Create a mock HTTP request
		MockHttpServletRequest request = new MockHttpServletRequest();
		String jwtToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkaXZ5YUBnbWFpbC5jb20iLCJhdXRob3JpdGllcyI6InJlYWQsdXBkYXRlLGNyZWF0ZSxST0xFX01BTkFHRVIsZGVsZXRlIiwiaWF0IjoxNzE2ODc0NjU2LCJleHAiOjE3MTcyMjAyNTZ9.7v30UW78Vq7LWDBIVL-lMAzREeAtBDWpbJtn-knWfzc";
        request.addHeader("Authorization", "Bearer " + jwtToken);
		
		Customer customer = new Customer();
		customer.setCustomerEmail("sahana@gmail.com");
		AddressDTO addressDTO=AddressDTO.builder().addressLine("10th cross")
				                                  .customerCountry("xyz")
				                                  .customerState("xyz")
				                                  .build();
		
		// Mock the behavior of customerRepository to return the mock Customer
		when(customerRepository.findByCustomerEmail("sahana@gmail.com")).thenReturn(customer);
		
		// Mock the JWT service to return a specific username extracted from the token
		when(jwtService.extractUsername(jwtToken)).thenReturn("divya@gmail.com");
		
		Optional<User> user = Optional.of(User.builder().email("divya@gmail.com").build());
				
		// Mock the behavior of userRepository to return the mock User    
        when(userRepository.findByEmail("divya@gmail.com")).thenReturn(user);
        
        Address address=new Address();
        address.setAddressLine("10th cross");
        
        // Mock the behavior of addressRepository to save the address and return the mock Address
        when(addressRepository.save(any(Address.class))).thenReturn(address);
        // Call the method under test: addAddress in the addressService
        addressService.addAddress(addressDTO, "sahana@gmail.com", request);
        
	}

}
