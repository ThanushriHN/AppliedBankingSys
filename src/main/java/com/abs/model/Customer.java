package com.abs.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int customerId;
	private String customerFirstName;
	private String customerLastName;
	private String customerEmail;
	private String customerPassword;
	private long contact;
	private String addedBy;
	private LocalDateTime addedOn;
	private String updatedBy;
	private LocalDateTime updatedOn;
	@OneToMany(mappedBy = "customer")
	List<Address> addresses;
	@OneToMany(mappedBy = "customer")
	List<FixedDepositAccount> accounts;
}
