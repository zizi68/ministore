package com.trinh.webapi.dto;

import com.trinh.webapi.model.OrderDetail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderSummaryDTO {
	private OrderDetail orderDetail;
	private int size;
}
