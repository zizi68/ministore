package com.moht1.webapi.repository;

import java.util.List;

import com.moht1.webapi.model.Order;
import com.moht1.webapi.model.OrderDetail;
import com.moht1.webapi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer>{

	public List<OrderDetail> findByOrder(Order order);
	public List<OrderDetail> findByProduct(Product product);
	
}
