package com.abs.model;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FixedDepositAccount {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accountId;

    private double principal;
    private int termInMonths;
    private double interestRate;
    private double maturityAmount;
    private String accountNumber;
    private String branch;
    private String addedBy;
    private String updatedBy;
    private LocalDateTime addedOn;
    private LocalDateTime updatedOn;
    
    @ManyToOne
    Customer customer;

}
