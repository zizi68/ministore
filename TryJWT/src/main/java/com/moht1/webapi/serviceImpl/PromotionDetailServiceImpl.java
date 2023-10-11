package com.moht1.webapi.serviceImpl;

import java.util.List;

import com.moht1.webapi.model.Promotion;
import com.moht1.webapi.model.PromotionDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moht1.webapi.repository.ProductRepository;
import com.moht1.webapi.repository.PromotionDetailRepository;
import com.moht1.webapi.repository.PromotionRepository;
import com.moht1.webapi.service.PromotionDetailService;

@Transactional
@Service
public class PromotionDetailServiceImpl implements PromotionDetailService {

	@Autowired
	PromotionDetailRepository promotionDetailRepository;
	
	@Autowired
	PromotionRepository promotionRepository;
	
	@Autowired
	ProductRepository productRepository;

	@Override
	public List<PromotionDetail> saveListPromotionDetail(List<PromotionDetail> promotionDetails) {
		// TODO Auto-generated method stub
		return promotionDetailRepository.saveAll(promotionDetails);
	}

	@Override
	public List<PromotionDetail> findAllByPromotionId(Integer promotionId) {
		// TODO Auto-generated method stub
		Promotion promotion = promotionRepository.getById(promotionId);
		return promotionDetailRepository.findByPromotion(promotion);
	}

	@Override
	public List<PromotionDetail> updateListPromotionDetail(Integer promotionId,
			List<PromotionDetail> promotionDetails) {
		// TODO Auto-generated method stub
		Promotion promotion = promotionRepository.getById(promotionId);
		List<PromotionDetail> oldList = promotionDetailRepository.findByPromotion(promotion);

		promotionDetailRepository.deleteByPromotion(promotion);
		return promotionDetailRepository.saveAll(promotionDetails);
	}
	
}
