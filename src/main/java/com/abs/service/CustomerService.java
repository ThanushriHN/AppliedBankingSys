package com.abs.service;


import java.time.LocalDateTime;
import java.util.Optional;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.abs.dto.CustomerDTO;
import com.abs.model.Customer;
import com.abs.model.User;
import com.abs.repository.CustomerRepository;
import com.abs.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class CustomerService {

	private CustomerRepository customerRepository;
	private UserRepository userRepository;
	private JwtService jwtService;
	
	 
	public ResponseEntity<CustomerDTO> addCustomer(CustomerDTO customerDto,HttpServletRequest request) {
		
		Customer customer = mapToCustomer(customerDto);
	
		final String authHeader = request.getHeader("Authorization");
		final String jwt;
		final String email;

		// Extract jwt from the Authorization
		jwt = authHeader.substring(7);

		// Verify whether user is present in db and whether token is valid
		email = jwtService.extractUsername(jwt);
		Optional<User> user = userRepository.findByEmail(email);
		
		customer.setAddedBy(user.get().getFirstName() + " " +user.get().getLastName());
		customer.setUpdatedBy(user.get().getFirstName() + " " +user.get().getLastName());
		return ResponseEntity.ok(mapToCustomerDto(customerRepository.save(customer)));
	}


	 CustomerDTO mapToCustomerDto(Customer customer) {
		return CustomerDTO.builder()
				                  .customerFirstName(customer.getCustomerFirstName())
				                 .customerLastName(customer.getCustomerLastName())
				                 .customerEmail(customer.getCustomerEmail())
				                 .customerPassword(customer.getCustomerPassword())
				                 .contact(customer.getContact())
				                 .build();
	}


	 Customer mapToCustomer(CustomerDTO customerDto) {
		
		return Customer.builder().customerFirstName(customerDto.getCustomerFirstName())
				                 .customerLastName(customerDto.getCustomerLastName())
				                 .customerEmail(customerDto.getCustomerEmail())
				                 .customerPassword(customerDto.getCustomerPassword())
				                 .contact(customerDto.getContact())
				                 .addedOn(LocalDateTime.now())
				                 .updatedOn(LocalDateTime.now())
				                 .build();
	}


	public ResponseEntity<CustomerDTO> deleteCustomerByEmail( String customerEmail) {
		Customer customer=customerRepository.findByCustomerEmail(customerEmail);
		customerRepository.delete(customer);
		return ResponseEntity.ok(mapToCustomerDto(customer));
		
	}
		
}
