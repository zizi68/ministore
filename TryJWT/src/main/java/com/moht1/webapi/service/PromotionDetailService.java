package com.moht1.webapi.service;

import java.util.List;

import com.moht1.webapi.model.PromotionDetail;

public interface PromotionDetailService {

	public List<PromotionDetail> saveListPromotionDetail(List<PromotionDetail> promotionDetails);
	public List<PromotionDetail> updateListPromotionDetail(Integer promotionId, List<PromotionDetail> promotionDetails);
	public List<PromotionDetail> findAllByPromotionId(Integer promotionId);
	
}
