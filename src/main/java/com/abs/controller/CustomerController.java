package com.abs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abs.dto.AddressDTO;
import com.abs.dto.CustomerDTO;
import com.abs.dto.FixedDepositDTO;
import com.abs.dto.ReturnAmount;
import com.abs.service.AddressService;
import com.abs.service.CustomerService;
import com.abs.service.FixedDepositAccountService;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	CustomerService customerService;
	
	@Autowired
	FixedDepositAccountService depositAccountService;
	@Autowired
	AddressService addressService;

	@PostMapping("/createCustomer")
	public ResponseEntity<CustomerDTO> addCustomer(@RequestBody CustomerDTO customerDto, HttpServletRequest request) {	
		return customerService.addCustomer(customerDto,request);
	}
	
	@PostMapping("/createAddress/{customerEmail}")
	public ResponseEntity<AddressDTO> addAddress(@RequestBody AddressDTO addressDto,@PathVariable String customerEmail , HttpServletRequest request) {	
		return addressService.addAddress(addressDto,customerEmail,request);
	}
	
	@DeleteMapping("/deleteCustomer/{customerEmail}")
	public ResponseEntity<CustomerDTO> deleteCustomerByEmail(@PathVariable String customerEmail){
	    return customerService.deleteCustomerByEmail(customerEmail);	
	}
	
	@PostMapping("/createFixedDepositAccount/{customerEmail}")
	public ResponseEntity<FixedDepositDTO> createFixedDepositAccount(@RequestBody FixedDepositDTO depositAccountDTO,@PathVariable String customerEmail , HttpServletRequest request) {	
		return depositAccountService.createFixedDepositAccount(depositAccountDTO,customerEmail,request);
	}
	
	@DeleteMapping("/closeAccount/{accountId}")
	public ReturnAmount closeAccount(@PathVariable int accountId){
		return depositAccountService.closeAccount(accountId);
	}
	
	@GetMapping("/getAccountDetails/{accountId}")
	public ResponseEntity<FixedDepositDTO> getAccountDetails(@PathVariable int accountId){
		return depositAccountService.getAccountDetails(accountId);
	}
	
}
