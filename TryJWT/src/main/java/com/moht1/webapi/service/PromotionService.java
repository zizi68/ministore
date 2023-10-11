package com.moht1.webapi.service;

import java.util.Date;
import java.util.List;

import com.moht1.webapi.model.Product;
import com.moht1.webapi.model.Promotion;

public interface PromotionService {

	public Promotion findById(Integer id);
	public Promotion addPromotion(Promotion promotion);
	public Promotion editPromotion(Promotion promotion);
	public void deletePromotion(Promotion promotion);
	public List<Promotion> findAllByStatus(String status);
	public Date convertStringToDate(String dateTime);
	public int getCurrentPromotionByProduct(Product product);
	public String checkPromotionByProduct(Product product, Date startDate, Date finishDate);
	public List<Promotion> findPromotion(Date startDate, Date finishDate);
}
