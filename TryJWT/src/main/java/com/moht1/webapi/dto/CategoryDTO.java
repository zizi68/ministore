package com.moht1.webapi.dto;

import com.moht1.webapi.model.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
	private Category category;
	private int soldQuantity;
}
