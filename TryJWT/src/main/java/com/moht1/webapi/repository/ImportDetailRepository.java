package com.moht1.webapi.repository;

import java.util.List;

import com.moht1.webapi.model.Import;
import com.moht1.webapi.model.ImportDetail;
import com.moht1.webapi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImportDetailRepository extends JpaRepository<ImportDetail, Integer>{

	public List<ImportDetail> findByImports(Import imports);
	
	public List<ImportDetail> findByProductOrderByIdDesc(Product product);
	
}
