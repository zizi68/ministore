package com.trinh.webapi.service;

import com.trinh.webapi.model.OrderStatus;

public interface OrderStatusService {

	public OrderStatus findById(Integer id);
}
