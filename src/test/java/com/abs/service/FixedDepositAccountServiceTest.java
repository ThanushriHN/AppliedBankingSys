package com.abs.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import com.abs.dto.FixedDepositDTO;
import com.abs.dto.ReturnAmount;
import com.abs.model.Customer;
import com.abs.model.FixedDepositAccount;
import com.abs.model.User;
import com.abs.repository.CustomerRepository;
import com.abs.repository.FixedAccountRepository;
import com.abs.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class FixedDepositAccountServiceTest {
	
	@InjectMocks
	FixedDepositAccountService accountService;
	
	@Mock
	FixedAccountRepository accountRepository;
	
	@Mock
	CustomerRepository customerRepository;
	
	 @Mock 
	 private JwtService jwtService;
	 
	 @Mock
	  private UserRepository userRepository;
	 
	 
	 
	 @BeforeEach
	    void setUp() {
	        MockitoAnnotations.openMocks(this);
	 }
	
	 
	 /*
	  * Verify the successful creation of a fixed deposit account.
	  * Verify the accountService correctly processes the input data and interacts 
	  * with various components to create fixed deposit account.
	  */
	 @Test
	 void openAccountSuccessCase() {

	     // Creating a mock HTTP request to simulate an incoming API call
	     MockHttpServletRequest request = new MockHttpServletRequest();
	     
	     // Creating a JWT token to simulate an authenticated user
	     String jwtToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkaXZ5YUBnbWFpbC5jb20iLCJhdXRob3JpdGllcyI6InJlYWQsdXBkYXRlLGNyZWF0ZSxST0xFX01BTkFHRVIsZGVsZXRlIiwiaWF0IjoxNzE2ODc0NjU2LCJleHAiOjE3MTcyMjAyNTZ9.7v30UW78Vq7LWDBIVL-lMAzREeAtBDWpbJtn-knWfzc";
	     request.addHeader("Authorization", "Bearer " + jwtToken);
	     
	     Customer customer = new Customer();
	     customer.setCustomerEmail("sahana@gmail.com");
	     
	      
	     FixedDepositDTO dto = FixedDepositDTO.builder()
	                                          .principal(10000.0)
	                                          .termInMonths(94)
	                                          .branch("xyz")
	                                          .accountNumber("123")
	                                          .build();
	     
	     // Mock the customerRepository to return the customer when searched by email
	     when(customerRepository.findByCustomerEmail("sahana@gmail.com")).thenReturn(customer);
	     
	     // Mock the jwtService to return a userName when extracting it from the JWT token
	     when(jwtService.extractUsername(jwtToken)).thenReturn("divya@gmail.com");
	     
	     // Creating an Optional User object with the email from the JWT token
	     Optional<User> user = Optional.of(User.builder().email("divya@gmail.com").build());
	     
	     // Mock the userRepository to return the user when searched by email
	     when(userRepository.findByEmail("divya@gmail.com")).thenReturn(user);
	     
	     
	     FixedDepositAccount fixedDepositAccount = new FixedDepositAccount();
	     fixedDepositAccount.setPrincipal(10000.0);
	     
	     // Mock the accountRepository to return the fixedDepositAccount when saving a new account
	     when(accountRepository.save(any(FixedDepositAccount.class))).thenReturn(fixedDepositAccount);
	     
	     // Calling the createFixedDepositAccount method on the accountService with the DTO, customer email, and request
	     accountService.createFixedDepositAccount(dto, "sahana@gmail.com", request);
	 }

	
	
	 /*
	  * Verify the successful retrieval of fixed deposit account details.
	  * Verifies that the accountService correctly retrieves and returns the account details.
	  */
	 @Test
	 void testGetAccountDetails_Success() {
	     
	     // Define the account ID to be retrieved
	     int accountId = 1;
	     
	     FixedDepositAccount mockAccount = new FixedDepositAccount();
	     mockAccount.setPrincipal(1000.0);

	     // Mock the accountRepository to return the mock account when searched by ID
	     when(accountRepository.findById(accountId)).thenReturn(Optional.of(mockAccount));

	     FixedDepositDTO expectedDto = new FixedDepositDTO();
	     expectedDto.setPrincipal(1000.0);

	     // Call the getAccountDetails method on the accountService with the account ID
	     ResponseEntity<FixedDepositDTO> response = accountService.getAccountDetails(accountId);

	     // Assert that the response is as expected (an OK response with the expected DTO)
	     assertEquals(ResponseEntity.ok(expectedDto), response);
	 }

	  /* Verifies the successful closing of a fixed deposit account.
	  * Mock the account repository to return the account when searched by its ID.
	  * Invoke the closeAccount method of the accountService with the account ID.
	  * Assert that the returned amount matches the expected return amount.
	  */
	 @Test
	 void testCloseAccountSuccess() {
	    
	     int accountId = 1;
	    
	     Customer customer = new Customer();
	     
	     FixedDepositAccount account = new FixedDepositAccount();
	    
	     ReturnAmount expectedReturnAmount = new ReturnAmount();
	     account.setCustomer(customer);
	     account.setAddedOn(LocalDateTime.now().minusMonths(2));
	     account.setTermInMonths(6);
	     
	     customer.setAccounts(new ArrayList<>());
	     customer.getAccounts().add(account);
	     
	     // Mock the account repository to return the account when searched by its ID
	     when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
	     
	     // Invoke the closeAccount method of the accountService with the account ID
	     ReturnAmount result = accountService.closeAccount(accountId);

	     // Assert that the returned amount matches the expected return amount
	     assertEquals(expectedReturnAmount, result);
	 }

	
	 
	  // Test case to verify the behavior when creating a fixed deposit account with an invalid deposit amount.
	 @Test
	 void testInvalidDepositAmount() {
	     // Set up mock HTTP request and JWT token
	     MockHttpServletRequest request = new MockHttpServletRequest();
	     String jwtToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkaXZ5YUBnbWFpbC5jb20iLCJhdXRob3JpdGllcyI6InJlYWQsdXBkYXRlLGNyZWF0ZSxST0xFX01BTkFHRVIsZGVsZXRlIiwiaWF0IjoxNzE2MzU1NTczLCJleHAiOjE3MTY3MDExNzN9.xSpzPIibRvo6Egt1SzkzxaXlLlocGz4pkm6Tz8lUuRE";
	     request.addHeader("Authorization", "Bearer " + jwtToken);
	     
	     // Create a FixedDepositDTO with an invalid deposit amount
	     FixedDepositDTO depositDTO = new FixedDepositDTO();
	     depositDTO.setPrincipal(10.0);
	     depositDTO.setTermInMonths(99);

	     // Assert that an IllegalArgumentException is thrown with the expected message
	     IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
	         accountService.createFixedDepositAccount(depositDTO, "kavya@gmail.com", request);
	     });
	     assertEquals("Minimum deposit amount not met.", exception.getMessage());
	 }

	
	
	 // Test case to verify the behavior when creating a fixed deposit account with an invalid term in months.
	@Test
	void testInvalidTermsInMonth() {
	    // Set up mock HTTP request and JWT token
	    MockHttpServletRequest request = new MockHttpServletRequest();
	    String jwtToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkaXZ5YUBnbWFpbC5jb20iLCJhdXRob3JpdGllcyI6InJlYWQsdXBkYXRlLGNyZWF0ZSxST0xFX01BTkFHRVIsZGVsZXRlIiwiaWF0IjoxNzE2MzU1NTczLCJleHAiOjE3MTY3MDExNzN9.xSpzPIibRvo6Egt1SzkzxaXlLlocGz4pkm6Tz8lUuRE";
	    request.addHeader("Authorization", "Bearer " + jwtToken);
	    
	    // Create a FixedDepositDTO with invalid term
	    FixedDepositDTO depositDTO = new FixedDepositDTO();
	    depositDTO.setPrincipal(100000.0);
	    depositDTO.setTermInMonths(0);

	    // Assert that an IllegalArgumentException is thrown with the expected message
	    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
	        accountService.createFixedDepositAccount(depositDTO, "kavya@gmail.com", request);
	    });
	    assertEquals("Invalid term.", exception.getMessage());
	}

	
	
	
	 // Test case for an account that does not exist.
	@Test
	void testGetAccountDetails_NotFound() {
	   
	    int accountId = 1;

	    // Stub the behavior of the accountRepository to return an empty Optional
	    when(accountRepository.findById(accountId)).thenReturn(Optional.empty());
	    // Invoke the method under test
	    ResponseEntity<FixedDepositDTO> response = accountService.getAccountDetails(accountId);
	    // Verify that the response status code is NOT_FOUND
	    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	    // Verify that the response body is null
	    assertNull(response.getBody());
	}


	
	/* Test case to verify the behavior when attempting to close a fixed deposit account
	 * that should fail due to the term not being met.
	 */
	@Test
	void testCloseAccountFailure() {
	    
	    int accountId = 1;
	    
	    // Create a mock customer and account
	    Customer customer = new Customer();
	    FixedDepositAccount account = new FixedDepositAccount();
	    account.setCustomer(customer);
	    account.setAddedOn(LocalDateTime.now().minusMonths(2)); // Account added 2 months ago
	    account.setTermInMonths(1); // Term is 1 month
	    customer.setAccounts(new ArrayList<>());
	    customer.getAccounts().add(account);

	    // Stub the behavior of the accountRepository to return the mock account
	    when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

	    // Invoke the method under test
	    ReturnAmount result = accountService.closeAccount(accountId);
	    // Verify that the result is not null
	    assertNotNull(result);
	    // Verify that the maturity amount is zero, indicating a failed account closure
	    assertEquals(0.0, result.getMaturityAmount());
	}

	
}




