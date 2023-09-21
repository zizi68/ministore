package com.trinh.webapi.dto;

import com.trinh.webapi.model.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FullCart {

	private Integer id;

	private FullProduct product;

	private User user;

	private int quantity;
}
