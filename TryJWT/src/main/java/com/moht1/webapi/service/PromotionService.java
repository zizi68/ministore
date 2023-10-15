package com.moht1.webapi.service;

import com.moht1.webapi.model.Product;
import com.moht1.webapi.model.Promotion;

import java.util.Date;
import java.util.List;

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
