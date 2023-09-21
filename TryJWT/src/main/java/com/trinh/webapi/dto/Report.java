package com.trinh.webapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Report {
	
	private String label;
	private Long revenue;
	private Long funds;
	private Long returns;
	private Long profit;
	private Long profitRate;

}
