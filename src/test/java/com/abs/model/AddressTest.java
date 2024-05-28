package com.abs.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import jakarta.persistence.EntityManager;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AddressTest {
	
	 @Autowired
	 private EntityManager entityManager;
	 @Mock
	 private Customer customer;
	 
	 @BeforeEach
	    public void setUp() {
	        
	        customer = new Customer();
	        customer.setCustomerFirstName("Raj");
	        
	        entityManager.persist(customer);
	        entityManager.flush();
	    }

	    @Test
	    void testAddressEntity() {
	       
	        Address address = Address.builder()
	                .addressLine("10th cross")
	                .customerState("xyz")
	                .customerCountry("xyz")
	                .addedBy("MEMBER")
	                .addedOn(LocalDateTime.now())
	                .updatedBy("MEMBER")
	                .updatedOn(LocalDateTime.now())
	                .customer(customer)
	                .build();

	        
	        entityManager.persist(address);
	        entityManager.flush(); 
	        Address found = entityManager.find(Address.class, address.getAddressId());

	        assertThat(found).isNotNull();
	        assertThat(found.getAddressLine()).isEqualTo("10th cross");
	        assertThat(found.getCustomerState()).isEqualTo("xyz");
	        assertThat(found.getCustomerCountry()).isEqualTo("xyz");
	        assertThat(found.getAddedBy()).isEqualTo("MEMBER");
	        assertThat(found.getCustomer()).isEqualTo(customer);
	    }

	    @Test
	    void testAddressSettersAndGetters() {
	        
	        Address address = new Address();
	        address.setAddressId(1);
	        address.setAddressLine("10th cross");
	        address.setCustomerState("xyz");
	        address.setCustomerCountry("xyz");
	        address.setAddedBy("MEMBER");
	        address.setAddedOn(LocalDateTime.now());
	        address.setUpdatedBy("MEMBER");
	        address.setUpdatedOn(LocalDateTime.now());
	        address.setCustomer(customer);

	        
	        assertThat(address.getAddressId()).isEqualTo(1);
	        assertThat(address.getAddressLine()).isEqualTo("10th cross");
	        assertThat(address.getCustomerState()).isEqualTo("xyz");
	        assertThat(address.getCustomerCountry()).isEqualTo("xyz");
	        assertThat(address.getAddedBy()).isEqualTo("MEMBER");
	        assertThat(address.getCustomer()).isEqualTo(customer);
	    }

	    

	   

	    @Test
	    void testNullValues() {
	     
	        Address address = Address.builder()
	                .addressLine(null)
	                .customerState(null)
	                .customerCountry(null)
	                .addedBy(null)
	                .addedOn(null)
	                .updatedBy(null)
	                .updatedOn(null)
	                .customer(null)
	                .build();

	        entityManager.persist(address);
	        entityManager.flush();

	        Address found = entityManager.find(Address.class, address.getAddressId());

	        assertThat(found).isNotNull();
	        assertThat(found.getAddressLine()).isNull();
	        assertThat(found.getCustomerState()).isNull();
	        assertThat(found.getCustomerCountry()).isNull();
	        assertThat(found.getAddedBy()).isNull();
	        assertThat(found.getCustomer()).isNull();
	    }
	    
	    @Test
	    void testBuilder() {
	       Address.builder().toString();
	    }
	}