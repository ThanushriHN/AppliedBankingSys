package com.abs.dto;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
 class CustomerDTOTest {

    @Test
    void testAllArgsConstructor() {
       
        String firstName = "Thanu";
        String lastName = "shti";
        String email = "thanu@gmail.com";
        String password = "1234";
        long contact = 1234567890L;

        CustomerDTO customer = new CustomerDTO(firstName, lastName, email, password, contact);
     
        assertThat(customer.getCustomerFirstName()).isEqualTo(firstName);
        assertThat(customer.getCustomerLastName()).isEqualTo(lastName);
        assertThat(customer.getCustomerEmail()).isEqualTo(email);
        assertThat(customer.getCustomerPassword()).isEqualTo(password);
        assertThat(customer.getContact()).isEqualTo(contact);
    }

    @Test
    void testNoArgsConstructorAndSetters() {
        
        CustomerDTO customer = new CustomerDTO();
        String firstName = "Thanu";
        String lastName = "shri";
        String email = "thanu@gmail.com";
        String password = "1234";
        long contact = 1234567890L;
       
        customer.setCustomerFirstName(firstName);
        customer.setCustomerLastName(lastName);
        customer.setCustomerEmail(email);
        customer.setCustomerPassword(password);
        customer.setContact(contact);

        assertThat(customer.getCustomerFirstName()).isEqualTo(firstName);
        assertThat(customer.getCustomerLastName()).isEqualTo(lastName);
        assertThat(customer.getCustomerEmail()).isEqualTo(email);
        assertThat(customer.getCustomerPassword()).isEqualTo(password);
        assertThat(customer.getContact()).isEqualTo(contact);
    }
    
    @Test
    void testBuilder() {
    	new CustomerDTO().builder().toString();
    }
}

