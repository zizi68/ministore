package com.trinh.webapi.dto;

import java.util.List;

import com.trinh.webapi.model.Brand;
import com.trinh.webapi.model.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BrandDTO {
	private Brand brand;
	private int soldQuantity;
}
