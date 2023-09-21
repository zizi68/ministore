package com.trinh.webapi.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class CartRequest {

	private Integer productId;
	private Integer userId;
	private int quantity;
	
}
