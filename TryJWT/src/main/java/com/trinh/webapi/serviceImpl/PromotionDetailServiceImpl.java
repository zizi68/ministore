package com.trinh.webapi.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trinh.webapi.model.ImportDetail;
import com.trinh.webapi.model.OrderDetail;
import com.trinh.webapi.model.Product;
import com.trinh.webapi.model.Promotion;
import com.trinh.webapi.model.PromotionDetail;
import com.trinh.webapi.repository.ImportDetailRepository;
import com.trinh.webapi.repository.ImportRepository;
import com.trinh.webapi.repository.OrderDetailRepository;
import com.trinh.webapi.repository.OrderRepository;
import com.trinh.webapi.repository.OrderStatusRepository;
import com.trinh.webapi.repository.ProductRepository;
import com.trinh.webapi.repository.PromotionDetailRepository;
import com.trinh.webapi.repository.PromotionRepository;
import com.trinh.webapi.service.ImportDetailService;
import com.trinh.webapi.service.OrderDetailService;
import com.trinh.webapi.service.PromotionDetailService;

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
