package com.trinh.webapi.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trinh.webapi.model.Order;
import com.trinh.webapi.model.OrderDetail;
import com.trinh.webapi.model.OrderStatus;
import com.trinh.webapi.model.User;
import com.trinh.webapi.repository.OrderDetailRepository;
import com.trinh.webapi.repository.OrderRepository;
import com.trinh.webapi.repository.OrderStatusRepository;
import com.trinh.webapi.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	OrderStatusRepository orderStatusRepository;
	
	@Autowired
	OrderDetailRepository orderDetailRepository;

	@Override
	public Order findById(Integer id) {
		Optional<Order> order = orderRepository.findById(id);
		if(!order.isPresent()) {
			return null;
		}
		return order.get();
	}

	@Override
	public List<Order> findByStatusOrderByDateDesc(OrderStatus orderStatus) {
		return orderRepository.findByStatusOrderByDateDesc(orderStatus);
	}

	@Override
	public List<OrderDetail> findOrderDetailByOrderId(Integer orderId) {
		Order order = orderRepository.getById(orderId);
		List<OrderDetail> list = orderDetailRepository.findByOrder(order);
		return list;
	}

	@Override
	public Order updateOrder(Order order, Integer statusId) {
		OrderStatus status = orderStatusRepository.getById(statusId);
		order.setStatus(status);
		return orderRepository.save(order);
	}

	@Override
	public List<Order> findByUserAndStatusOrderByDateDesc(User user, OrderStatus orderStatus) {
		return orderRepository.findByUserAndStatusOrderByDateDesc(user, orderStatus);
	}

	@Override
	public List<Order> getAllByUser(User user) {
		// TODO Auto-generated method stub
		return orderRepository.findByUserOrderByDateDesc(user);
	}

	@Override
	public List<Order> findByStatusOrderAndShipperByDateDesc(OrderStatus orderStatus, User shipper) {
		// TODO Auto-generated method stub
		return orderRepository.findByShipperAndStatusOrderByDateDesc(shipper, orderStatus);
	}
}
