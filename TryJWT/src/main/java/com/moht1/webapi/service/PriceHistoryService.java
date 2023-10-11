package com.moht1.webapi.service;

import java.util.List;

import com.moht1.webapi.model.PriceHistory;
import com.moht1.webapi.model.Product;

public interface PriceHistoryService {

	public List<PriceHistory> findByProduct(Product product);
	public PriceHistory findByProductOrderByPrice(Product product, String option);
	public void addPriceHistory(PriceHistory priceHistory);
	public Float getLatestPrice(Product product);
}
