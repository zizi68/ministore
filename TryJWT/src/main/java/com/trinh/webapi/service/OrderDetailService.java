package com.trinh.webapi.service;

import java.util.List;

import com.trinh.webapi.model.Order;
import com.trinh.webapi.model.OrderDetail;

public interface OrderDetailService {

	public List<OrderDetail> saveListOrderDetail(List<OrderDetail> orderDetails);
	public boolean updateSoldQuantityByOrderDetail(List<OrderDetail> orderDetails, int type);
	public OrderDetail findById(Integer id);
	
}
