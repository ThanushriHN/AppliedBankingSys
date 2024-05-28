package com.abs.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.abs.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>{

	//@Query(value = "select * from customer where customerEmail=:customerEmail", nativeQuery = true)
	Customer findByCustomerEmail(String customerEmail);
	


}
