package com.moht1.webapi.service;

import com.moht1.webapi.model.PromotionDetail;

import java.util.List;

public interface PromotionDetailService {

    public List<PromotionDetail> saveListPromotionDetail(List<PromotionDetail> promotionDetails);

    public List<PromotionDetail> updateListPromotionDetail(Integer promotionId, List<PromotionDetail> promotionDetails);

    public List<PromotionDetail> findAllByPromotionId(Integer promotionId);

}
