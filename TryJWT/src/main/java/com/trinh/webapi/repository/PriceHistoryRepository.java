package com.trinh.webapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trinh.webapi.model.Brand;
import com.trinh.webapi.model.PriceHistory;
import com.trinh.webapi.model.Product;

@Repository
public interface PriceHistoryRepository extends JpaRepository<PriceHistory, Integer>{
	public List<PriceHistory> findByProductOrderByDateDesc(Product product);
	public List<PriceHistory> findByProductOrderByPriceDesc(Product product);
	public List<PriceHistory> findByProductOrderByPriceAsc(Product product);
}