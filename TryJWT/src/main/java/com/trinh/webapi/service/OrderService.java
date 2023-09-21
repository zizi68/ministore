package com.trinh.webapi.service;

import java.util.List;

import com.trinh.webapi.model.Order;
import com.trinh.webapi.model.OrderDetail;
import com.trinh.webapi.model.OrderStatus;
import com.trinh.webapi.model.User;

public interface OrderService {

	public Order findById(Integer id);
	public List<Order> findByStatusOrderByDateDesc(OrderStatus orderStatus);
	public List<Order> findByStatusOrderAndShipperByDateDesc(OrderStatus orderStatus,User shipper);
	public List<OrderDetail> findOrderDetailByOrderId(Integer orderId);
	public Order updateOrder(Order order, Integer statusId);
	public List<Order> findByUserAndStatusOrderByDateDesc(User user, OrderStatus orderStatus);
	public List<Order> getAllByUser(User user);
}
