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

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address {
	
	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
      private int addressId;
      private String addressLine;
      private String customerState;
      private String customerCountry;
      private String addedBy;
  	  private LocalDateTime addedOn;
  	  private String updatedBy;
  	  private LocalDateTime updatedOn;
      @ManyToOne
      Customer customer;
}
