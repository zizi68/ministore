package com.trinh.webapi.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trinh.webapi.model.OrderStatus;
import com.trinh.webapi.repository.OrderDetailRepository;
import com.trinh.webapi.repository.OrderRepository;
import com.trinh.webapi.repository.OrderStatusRepository;
import com.trinh.webapi.service.OrderStatusService;

@Service
public class OrderStatusServiceImpl implements OrderStatusService{

	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	OrderStatusRepository orderStatusRepository;
	
	@Autowired
	OrderDetailRepository orderDetailRepository;


	@Override
	public OrderStatus findById(Integer id) {
		Optional<OrderStatus> orderStatusOptional = orderStatusRepository.findById(id);
		if(!orderStatusOptional.isPresent()) {
			return null;
		}
		return orderStatusOptional.get();
	}
	
}
