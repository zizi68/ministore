package com.trinh.webapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trinh.webapi.model.Import;
import com.trinh.webapi.model.ImportDetail;
import com.trinh.webapi.model.Order;
import com.trinh.webapi.model.OrderDetail;
import com.trinh.webapi.model.Product;

@Repository
public interface ImportDetailRepository extends JpaRepository<ImportDetail, Integer>{

	public List<ImportDetail> findByImports(Import imports);
	
	public List<ImportDetail> findByProductOrderByIdDesc(Product product);
	
}
