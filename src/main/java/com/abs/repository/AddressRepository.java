package com.abs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abs.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

}
