package com.abs.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.abs.dto.AddressDTO;
import com.abs.dto.CustomerDTO;
import com.abs.service.AddressService;
import com.abs.service.CustomerService;
import com.abs.service.FixedDepositAccountService;

import jakarta.servlet.http.HttpServletRequest;


@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    @Mock
    private FixedDepositAccountService depositAccountService;

    @Mock
    private AddressService addressService;

    @Mock
    private HttpServletRequest request;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void testAddCustomer() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerEmail("kavya@gmail.com");

        when(customerService.addCustomer(any(CustomerDTO.class), any(HttpServletRequest.class)))
                .thenReturn(ResponseEntity.ok(customerDTO));

        ResponseEntity<CustomerDTO> response = customerController.addCustomer(customerDTO, request);

        assertEquals("kavya@gmail.com", response.getBody().getCustomerEmail());
        
    }

    @Test
    void testAddAddress() {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setAddressLine("10th cross");

        when(addressService.addAddress(any(AddressDTO.class), eq("test@example.com"), any(HttpServletRequest.class)))
                .thenReturn(ResponseEntity.ok(addressDTO));

        ResponseEntity<AddressDTO> response = customerController.addAddress(addressDTO, "test@example.com", request);

        assertEquals("10th cross", response.getBody().getAddressLine());
        
    }

    @Test
    void testDeleteCustomerByEmail() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerEmail("kavya@gmail.com");

        when(customerService.deleteCustomerByEmail("kavya@gmail.com"))
                .thenReturn(ResponseEntity.ok(customerDTO));

        ResponseEntity<CustomerDTO> response = customerController.deleteCustomerByEmail("kavya@gmail.com");

        assertEquals("kavya@gmail.com", response.getBody().getCustomerEmail());
       
    }
}
