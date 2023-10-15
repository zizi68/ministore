package com.moht1.webapi.repository;

import com.moht1.webapi.model.Product;
import com.moht1.webapi.model.Promotion;
import com.moht1.webapi.model.PromotionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionDetailRepository extends JpaRepository<PromotionDetail, Integer> {

    public List<PromotionDetail> findByPromotionAndProduct(Promotion promotion, Product product);

    public List<PromotionDetail> findByPromotion(Promotion promotion);

    public List<PromotionDetail> deleteByPromotion(Promotion promotion);

}
