package com.trinh.webapi.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class FeedbackId implements Serializable {
	
	@Column(name = "user_id")
	private Integer userId;

	@Column(name = "product_id")
	private Integer productId;
	
	public FeedbackId() {
		super();
	}

	public FeedbackId(Integer userId, Integer productId) {
		super();
		this.userId = userId;
		this.productId = productId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	
}
