package com.trinh.webapi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "return_detail")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReturnDetail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	@ManyToOne
	@JoinColumn(name = "return_id")
	private Return return0;

	private int quantity;
	
	public ReturnDetail (Product product, Return return0,int quantity, float price)
	{
		this.product = product;
		this.return0 = return0;
		this.quantity = quantity;
	}
	
}
