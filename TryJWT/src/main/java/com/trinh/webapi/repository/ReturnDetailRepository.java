package com.trinh.webapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trinh.webapi.model.Order;
import com.trinh.webapi.model.OrderDetail;
import com.trinh.webapi.model.Product;
import com.trinh.webapi.model.Return;
import com.trinh.webapi.model.ReturnDetail;

@Repository
public interface ReturnDetailRepository extends JpaRepository<ReturnDetail, Integer>{

	public List<ReturnDetail> findByReturn0(Return return0);
	public List<ReturnDetail> findByProduct(Product product);
	
}
