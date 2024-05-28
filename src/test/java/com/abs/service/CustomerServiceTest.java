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
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import com.abs.dto.CustomerDTO;
import com.abs.model.Customer;
import com.abs.model.User;
import com.abs.repository.CustomerRepository;
import com.abs.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
	
	@InjectMocks
	CustomerService customerService;
	
	@Mock
	CustomerRepository customerRepository;
	
	@Mock
    private UserRepository userRepository;
	
	 @Mock
	private JwtService jwtService;
	
	@BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

	 @Test
	 void createCustomerTest() {
	       
	        MockHttpServletRequest request = new MockHttpServletRequest();
	        String jwtToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkaXZ5YUBnbWFpbC5jb20iLCJhdXRob3JpdGllcyI6InJlYWQsdXBkYXRlLGNyZWF0ZSxST0xFX01BTkFHRVIsZGVsZXRlIiwiaWF0IjoxNzE2ODc0NjU2LCJleHAiOjE3MTcyMjAyNTZ9.7v30UW78Vq7LWDBIVL-lMAzREeAtBDWpbJtn-knWfzc";
	        request.addHeader("Authorization", "Bearer " + jwtToken);

	        CustomerDTO customerDto = new CustomerDTO();
	        customerDto.setCustomerEmail("kavya@gmail.com");
	        customerDto.setCustomerFirstName("Kavya");
	        customerDto.setCustomerLastName("gowda");
	        customerDto.setCustomerPassword("123456");
	        
	        when(jwtService.extractUsername(jwtToken)).thenReturn("divya@gmail.com");
			
			Optional<User> user = Optional.of(User.builder().email("divya@gmail.com").build());      
	        when(userRepository.findByEmail("divya@gmail.com")).thenReturn(user);
	        Customer customer=new Customer();
	        customer.setCustomerFirstName("Kavya");
	        customer.setCustomerLastName("gowda");
	        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
	        customerService.addCustomer(customerDto, request);
	       
	    }
 
	 @Test
	 void testDeleteCustomerByEmail() {
	        
		 String customerEmail = "kavya@gmail.com";
	        Customer customer = new Customer();
	        customer.setCustomerEmail(customerEmail); 
	        CustomerDTO customerDTO = new CustomerDTO(); 
	        customerDTO.setCustomerEmail(customerEmail);
	        when(customerRepository.findByCustomerEmail(customerEmail)).thenReturn(customer);
	        ResponseEntity<CustomerDTO> response = customerService.deleteCustomerByEmail(customerEmail);
	       
	    }
}
