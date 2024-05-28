package com.abs.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abs.model.FixedDepositAccount;

@Repository
public interface FixedAccountRepository extends JpaRepository<FixedDepositAccount, Integer>{
 
}
