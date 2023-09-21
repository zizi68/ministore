package com.trinh.webapi.dto;

import com.trinh.webapi.model.ReturnDetail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReturnSummaryDTO {
	private ReturnDetail returnDetail;
	private int size;
}
