package com.moht1.webapi.repository;

import java.util.List;

import com.moht1.webapi.model.Product;
import com.moht1.webapi.model.Return;
import com.moht1.webapi.model.ReturnDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReturnDetailRepository extends JpaRepository<ReturnDetail, Integer>{

	public List<ReturnDetail> findByReturn0(Return return0);
	public List<ReturnDetail> findByProduct(Product product);
	
}
