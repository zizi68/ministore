package com.trinh.webapi.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.trinh.webapi.model.OrderDetail;
import com.trinh.webapi.model.OrderStatus;
import com.trinh.webapi.model.User;

public class OrderDTO {
	
	private Integer orderId;
	private Date date;
	private String address;
	private float totalPrice;
	private OrderStatus status;
	private User user;
	private List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
	
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public float getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}
	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}
	public OrderDTO() {
		super();
	}
	
	
}
