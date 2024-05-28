package com.abs.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FixedDepositDTO {
	
	private double principal;
    private int termInMonths;
    private String accountNumber;
    private String branch;
}
