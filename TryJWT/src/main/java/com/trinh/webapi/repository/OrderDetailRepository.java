package com.trinh.webapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trinh.webapi.model.Order;
import com.trinh.webapi.model.OrderDetail;
import com.trinh.webapi.model.Product;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer>{

	public List<OrderDetail> findByOrder(Order order);
	public List<OrderDetail> findByProduct(Product product);
	
}
