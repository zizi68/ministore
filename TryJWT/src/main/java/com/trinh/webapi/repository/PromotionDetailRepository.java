package com.trinh.webapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trinh.webapi.model.Import;
import com.trinh.webapi.model.ImportDetail;
import com.trinh.webapi.model.Order;
import com.trinh.webapi.model.OrderDetail;
import com.trinh.webapi.model.Product;
import com.trinh.webapi.model.Promotion;
import com.trinh.webapi.model.PromotionDetail;

@Repository
public interface PromotionDetailRepository extends JpaRepository<PromotionDetail, Integer>{

	public List<PromotionDetail> findByPromotionAndProduct(Promotion promotion, Product product);
	
	public List<PromotionDetail> findByPromotion(Promotion promotion);
	
	public List<PromotionDetail> deleteByPromotion(Promotion promotion);
	
}
