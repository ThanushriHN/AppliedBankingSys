package com.abs.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.abs.dto.AddressDTO;
import com.abs.model.Address;
import com.abs.model.Customer;
import com.abs.model.User;
import com.abs.repository.AddressRepository;
import com.abs.repository.CustomerRepository;
import com.abs.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class AddressService {
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AddressRepository addressRepository;
	

	public ResponseEntity<AddressDTO> addAddress(AddressDTO addressDto,String customerEmail ,HttpServletRequest request) {
		Customer customer=customerRepository.findByCustomerEmail(customerEmail);
		
		Address address=mapToAddress(addressDto);
		final String authHeader = request.getHeader("Authorization");
		final String jwt;
		final String email;
        
		
		jwt = authHeader.substring(7);
		email = jwtService.extractUsername(jwt);
		Optional<User> user = userRepository.findByEmail(email);
		address.setCustomer(customer);
		address.setAddedBy(user.get().getFirstName() + " " +user.get().getLastName());
		address.setUpdatedBy(user.get().getFirstName() + " " +user.get().getLastName());
		address.setAddedOn(LocalDateTime.now());
		address.setUpdatedOn(LocalDateTime.now());
		addressRepository.save(address);
		AddressDTO addressDTO=mapToAddressDTO(address);
		
		return ResponseEntity.ok(addressDTO);
	}

	private AddressDTO mapToAddressDTO(Address address) {
		return AddressDTO.builder().addressLine(address.getAddressLine())
				.customerCountry(address.getCustomerCountry())
				.customerState(address.getCustomerState())
				.build();
	}

	private Address mapToAddress(AddressDTO addressDto) {
		return Address.builder().addressLine(addressDto.getAddressLine())
				.customerCountry(addressDto.getCustomerCountry())
				.customerState(addressDto.getCustomerState())
				.addedOn(LocalDateTime.now())
				.updatedOn(LocalDateTime.now())
				.build();
	}

}
