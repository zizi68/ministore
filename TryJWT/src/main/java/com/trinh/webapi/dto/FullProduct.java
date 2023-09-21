package com.trinh.webapi.dto;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trinh.webapi.model.Brand;
import com.trinh.webapi.model.Cart;
import com.trinh.webapi.model.Category;
import com.trinh.webapi.model.OrderDetail;
import com.trinh.webapi.model.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FullProduct {

	private Integer id;

	private String name;

	private String description;

	private String image;

	private Float price;

	private String specification;

	private int discount;

	private int soldQuantity;

	private int quantity;

	private Category category;

	private Brand brand;

	private Boolean status;

	private String calculationUnit;
	
	public FullProduct(Product product, Float price, Integer discount) {
		this.id = product.getId();
		this.name = product.getName();
		this.description = product.getDescription();
		this.image = product.getImage();
		this.price = price;
		this.specification = product.getSpecification();
		this.discount = discount;
		this.soldQuantity = product.getSoldQuantity();
		this.quantity = product.getQuantity();
		this.category = product.getCategory();
		this.brand = product.getBrand();
		this.status = product.getStatus();
		this.calculationUnit = product.getCalculationUnit();		
	}
	
}
