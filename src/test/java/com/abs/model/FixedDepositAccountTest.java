package com.abs.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

class FixedDepositAccountTest {

    @Test
    void testNoArgsConstructor() {
        FixedDepositAccount account = new FixedDepositAccount();
        assertNotNull(account);
    }

    @Test
    void testAllArgsConstructor() {
        Customer customer = new Customer(); 
        LocalDateTime now = LocalDateTime.now();
        FixedDepositAccount account = new FixedDepositAccount(1, 1000.0, 12, 5.0, 1050.0, "123456", "xyz", "MEMBER", "MEMBER", now, now, customer);
        assertNotNull(account);
        assertEquals(1, account.getAccountId());
        assertEquals(1000.0, account.getPrincipal());
        assertEquals(12, account.getTermInMonths());
        assertEquals(5.0, account.getInterestRate());
        assertEquals(1050.0, account.getMaturityAmount());
        assertEquals("123456", account.getAccountNumber());
        assertEquals("xyz", account.getBranch());
        assertEquals("MEMBER", account.getAddedBy());
        assertEquals("MEMBER", account.getUpdatedBy());
        assertEquals(now, account.getAddedOn());
        assertEquals(now, account.getUpdatedOn());
        assertEquals(customer, account.getCustomer());
    }

    @Test
    void testBuilder() {
        Customer customer = new Customer(); 
        LocalDateTime now = LocalDateTime.now();
        FixedDepositAccount account = FixedDepositAccount.builder()
                .accountId(1)
                .principal(1000.0)
                .termInMonths(12)
                .interestRate(5.0)
                .maturityAmount(1050.0)
                .accountNumber("123456")
                .branch("xyz")
                .addedBy("MEMBER")
                .updatedBy("MEMBER")
                .addedOn(now)
                .updatedOn(now)
                .customer(customer)
                .build();

        assertNotNull(account);
        assertEquals(1, account.getAccountId());
        assertEquals(1000.0, account.getPrincipal());
        assertEquals(12, account.getTermInMonths());
        assertEquals(5.0, account.getInterestRate());
        assertEquals(1050.0, account.getMaturityAmount());
        assertEquals("123456", account.getAccountNumber());
        assertEquals("xyz", account.getBranch());
        assertEquals("MEMBER", account.getAddedBy());
        assertEquals("MEMBER", account.getUpdatedBy());
        assertEquals(now, account.getAddedOn());
        assertEquals(now, account.getUpdatedOn());
        assertEquals(customer, account.getCustomer());
    }

    @Test
    void testSettersAndGetters() {
        FixedDepositAccount account = new FixedDepositAccount();
        LocalDateTime now = LocalDateTime.now();
        Customer customer = new Customer(); 

        account.setAccountId(1);
        account.setPrincipal(1000.0);
        account.setTermInMonths(12);
        account.setInterestRate(5.0);
        account.setMaturityAmount(1050.0);
        account.setAccountNumber("123456");
        account.setBranch("xyz");
        account.setAddedBy("MEMBER");
        account.setUpdatedBy("MEMBER");
        account.setAddedOn(now);
        account.setUpdatedOn(now);
        account.setCustomer(customer);

        assertEquals(1, account.getAccountId());
        assertEquals(1000.0, account.getPrincipal());
        assertEquals(12, account.getTermInMonths());
        assertEquals(5.0, account.getInterestRate());
        assertEquals(1050.0, account.getMaturityAmount());
        assertEquals("123456", account.getAccountNumber());
        assertEquals("xyz", account.getBranch());
        assertEquals("MEMBER", account.getAddedBy());
        assertEquals("MEMBER", account.getUpdatedBy());
        assertEquals(now, account.getAddedOn());
        assertEquals(now, account.getUpdatedOn());
        assertEquals(customer, account.getCustomer());
    }
    
    @Test
    void test_Builder() {
    	FixedDepositAccount.builder().toString();
    }
}
