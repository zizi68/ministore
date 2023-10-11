package com.moht1.webapi.serviceImpl;

import java.util.List;

import com.moht1.webapi.model.PriceHistory;
import com.moht1.webapi.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moht1.webapi.repository.PriceHistoryRepository;
import com.moht1.webapi.repository.ProductRepository;
import com.moht1.webapi.service.PriceHistoryService;

@Service
public class PriceHistoryServiceImpl implements PriceHistoryService{

	@Autowired
	PriceHistoryRepository priceHistoryRepository;
	
	@Autowired
	ProductRepository productRepository;

	@Override
	public List<PriceHistory> findByProduct(Product product) {
		return priceHistoryRepository.findByProductOrderByDateDesc(product);
	}

	@Override
	public void addPriceHistory(PriceHistory priceHistory) {
		priceHistoryRepository.save(priceHistory);
	}

	@Override
	public Float getLatestPrice(Product product) {
		return priceHistoryRepository.findByProductOrderByDateDesc(product).get(0).getPrice();
	}

	@Override
	public PriceHistory findByProductOrderByPrice(Product product, String option) {
		if(option.equalsIgnoreCase("max"))
			return priceHistoryRepository.findByProductOrderByPriceDesc(product).get(0);
		else if(option.equalsIgnoreCase("min"))
			return priceHistoryRepository.findByProductOrderByPriceAsc(product).get(0);
		
		PriceHistory history = new PriceHistory();
		Float sum = (float) 0.0;
		List<PriceHistory> list = priceHistoryRepository.findByProductOrderByDateDesc(product);
		for(PriceHistory i : list) 
			sum += i.getPrice();
		
		history.setPrice(sum/list.size());
		
		return history;
	}

}
