package com.trinh.webapi.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trinh.webapi.model.Order;
import com.trinh.webapi.model.OrderDetail;
import com.trinh.webapi.model.Product;
import com.trinh.webapi.repository.OrderDetailRepository;
import com.trinh.webapi.repository.OrderRepository;
import com.trinh.webapi.repository.OrderStatusRepository;
import com.trinh.webapi.repository.ProductRepository;
import com.trinh.webapi.service.OrderDetailService;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	OrderStatusRepository orderStatusRepository;
	
	@Autowired
	OrderDetailRepository orderDetailRepository;
	
	@Autowired
	ProductRepository productRepository;

	@Override
	public List<OrderDetail> saveListOrderDetail(List<OrderDetail> orderDetails) {
		return orderDetailRepository.saveAll(orderDetails);
	}

	@Override
	public boolean updateSoldQuantityByOrderDetail(List<OrderDetail> orderDetails, int type) {
		/***
		 * type = 1: cộng thêm vào số lượng bán
		 * type = 2: trừ bớt số lượng bán
		 */
		if(type == 1) {
			for(OrderDetail item : orderDetails)
			{
				Product product = item.getProduct();
				if(product.getSoldQuantity() == product.getQuantity())
					return false;
			}
			
			for(OrderDetail item : orderDetails)
			{
				Product product = item.getProduct();
				product.setSoldQuantity(product.getSoldQuantity() + item.getQuantity());
				productRepository.save(product);
			}
			return true;
		}
		else {
			for(OrderDetail item : orderDetails)
			{
				Product product = item.getProduct();
				product.setSoldQuantity(product.getSoldQuantity() - item.getQuantity());
				productRepository.save(product);
			}
			return true;
		}
	}

	@Override
	public OrderDetail findById(Integer id) {
		Optional<OrderDetail> order = orderDetailRepository.findById(id);
		if(!order.isPresent()) {
			return null;
		}
		return order.get();
	}
	
}
