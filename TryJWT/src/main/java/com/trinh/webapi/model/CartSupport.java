package com.trinh.webapi.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.trinh.webapi.service.PriceHistoryService;

public class CartSupport {

	private final List<Cart> items;

	private BigDecimal total;
	
	@Autowired
	PriceHistoryService priceHistoryService;

	public CartSupport() {
		super();
		this.items = new ArrayList<Cart>();
		this.total = new BigDecimal(0);

	}

	public CartSupport(List<Cart> list) {
		super();
		this.items = list;
		this.total = new BigDecimal(0);
	}

	public Cart getItem(Product product) {
		for (Cart item : items) {
			if (item.getProduct().getId() == product.getId()) {
				return item;
			}
		}
		return null;
	}

	public int getItemCount() {
		return items.size();
	}

	public int addItem(Product product, int quantity) {
		Cart item = this.getItem(product);
		this.total = this.total.add(new BigDecimal(priceHistoryService.getLatestPrice(product)).multiply(new BigDecimal(quantity)));
		if (item != null) {// tồn tại rồi: ->update
			item.setQuantity(item.getQuantity() + quantity);

			return 1; // update
		} // chưa tồn tại: ->insert new
		else {
			item = new Cart();
			item.setProduct(product);
			item.setQuantity(quantity);
			items.add(item);

			return 2; // insert
		}
	}

	public void addItemFromDB(Cart itemsSample) {

		Cart item = new Cart();
		item.setId(itemsSample.getId());
		item.setProduct(itemsSample.getProduct());
		item.setQuantity(itemsSample.getQuantity());
		items.add(item);

		// update total:
		this.total = this.total
				.add(new BigDecimal(priceHistoryService.getLatestPrice(item.getProduct())).multiply(new BigDecimal(item.getQuantity())));
	}

	public void updateItem(Product product, int quantity) {
		Cart item = this.getItem(product);
		if (item != null) {
			item.setQuantity(quantity);

			this.total = BigDecimal.valueOf(0);

			this.callTotalPrice();
		}

	}

	public void removeItem(Product product) {
		Cart item = this.getItem(product);
		if (item != null) {
			items.remove(item);

			this.total = BigDecimal.valueOf(0);
			this.callTotalPrice();
		}
	}

	public void clear() {
		this.items.clear();
		this.total = BigDecimal.valueOf(0);
	}

	public boolean isEmpty() {
		return items.isEmpty();
	}

	public void callTotalPrice() {
		for (Cart i : items) {
			BigDecimal priceOfItem = new BigDecimal(priceHistoryService.getLatestPrice(i.getProduct()))
					.multiply(new BigDecimal(i.getQuantity()));

			total = total.add(priceOfItem);
		}
	}

}
