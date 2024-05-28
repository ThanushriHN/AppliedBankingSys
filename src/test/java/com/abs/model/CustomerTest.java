package com.abs.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import jakarta.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CustomerTest {

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    public void setUp() {
        // Initialize the entity manager and other required resources before each test
    }

    @Test
    void testCustomerEntity() {
        // Create a Customer entity
        Customer customer = Customer.builder()
                .customerFirstName("John")
                .customerLastName("Doe")
                .customerEmail("john.doe@example.com")
                .customerPassword("password123")
                .contact(1234567890L)
                .addedBy("admin")
                .addedOn(LocalDateTime.now())
                .updatedBy("admin")
                .updatedOn(LocalDateTime.now())
                .addresses(new ArrayList<>())
                .accounts(new ArrayList<>())
                .build();

        // Persist Customer entity
        entityManager.persist(customer);
        entityManager.flush();

        // Retrieve Customer entity by ID
        Customer found = entityManager.find(Customer.class, customer.getCustomerId());

        // Verify Customer entity properties
        assertThat(found).isNotNull();
        assertThat(found.getCustomerFirstName()).isEqualTo("John");
        assertThat(found.getCustomerLastName()).isEqualTo("Doe");
        assertThat(found.getCustomerEmail()).isEqualTo("john.doe@example.com");
        assertThat(found.getCustomerPassword()).isEqualTo("password123");
        assertThat(found.getContact()).isEqualTo(1234567890L);
        assertThat(found.getAddedBy()).isEqualTo("admin");
        assertThat(found.getAddresses()).isEmpty();
        assertThat(found.getAccounts()).isEmpty();
    }

    @Test
    void testCustomerSettersAndGetters() {
        // Create a Customer entity using the default constructor
        Customer customer = new Customer();
        customer.setCustomerId(1);
        customer.setCustomerFirstName("Jane");
        customer.setCustomerLastName("Doe");
        customer.setCustomerEmail("jane.doe@example.com");
        customer.setCustomerPassword("password456");
        customer.setContact(9876543210L);
        customer.setAddedBy("admin");
        customer.setAddedOn(LocalDateTime.now());
        customer.setUpdatedBy("admin");
        customer.setUpdatedOn(LocalDateTime.now());
        customer.setAddresses(new ArrayList<>());
        customer.setAccounts(new ArrayList<>());

        assertThat(customer.getCustomerId()).isEqualTo(1);
        assertThat(customer.getCustomerFirstName()).isEqualTo("Jane");
        assertThat(customer.getCustomerLastName()).isEqualTo("Doe");
        assertThat(customer.getCustomerEmail()).isEqualTo("jane.doe@example.com");
        assertThat(customer.getCustomerPassword()).isEqualTo("password456");
        assertThat(customer.getContact()).isEqualTo(9876543210L);
        assertThat(customer.getAddedBy()).isEqualTo("admin");
        assertThat(customer.getAddresses()).isEmpty();
        assertThat(customer.getAccounts()).isEmpty();
    }

    

    @Test
    void testNullValues() {
        
        Customer customer = Customer.builder()
                .customerFirstName(null)
                .customerLastName(null)
                .customerEmail(null)
                .customerPassword(null)
                .contact(0L)
                .addedBy(null)
                .addedOn(null)
                .updatedBy(null)
                .updatedOn(null)
                .addresses(null)
                .accounts(null)
                .build();

        entityManager.persist(customer);
        entityManager.flush();

        Customer found = entityManager.find(Customer.class, customer.getCustomerId());

        assertThat(found).isNotNull();
        assertThat(found.getCustomerFirstName()).isNull();
        assertThat(found.getCustomerLastName()).isNull();
        assertThat(found.getCustomerEmail()).isNull();
        assertThat(found.getCustomerPassword()).isNull();
        assertThat(found.getAddedBy()).isNull();
        assertThat(found.getAddresses()).isNull();
        assertThat(found.getAccounts()).isNull();
    }

    @Test
    void testBuilderPartialFields() {
        
        Customer customer = Customer.builder()
                .customerFirstName("Charlie")
                .customerLastName("Chaplin")
                .build();

        entityManager.persist(customer);
        entityManager.flush();

        Customer found = entityManager.find(Customer.class, customer.getCustomerId());

        assertThat(found).isNotNull();
        assertThat(found.getCustomerFirstName()).isEqualTo("Charlie");
        assertThat(found.getCustomerLastName()).isEqualTo("Chaplin");
        assertThat(found.getCustomerEmail()).isNull();
        assertThat(found.getCustomerPassword()).isNull();
        assertThat(found.getAddedBy()).isNull();
        assertThat(found.getAddresses()).isNull();
        assertThat(found.getAccounts()).isNull();
    }
}
