package com.trinh.webapi.service;

import java.util.List;

import com.trinh.webapi.model.PriceHistory;
import com.trinh.webapi.model.Product;

public interface PriceHistoryService {

	public List<PriceHistory> findByProduct(Product product);
	public PriceHistory findByProductOrderByPrice(Product product, String option);
	public void addPriceHistory(PriceHistory priceHistory);
	public Float getLatestPrice(Product product);
}
