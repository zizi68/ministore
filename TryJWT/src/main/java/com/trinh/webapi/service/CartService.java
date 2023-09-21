package com.trinh.webapi.service;

import java.util.List;

import com.trinh.webapi.model.Cart;
import com.trinh.webapi.model.Product;
import com.trinh.webapi.model.User;

public interface CartService {
  
	public Cart findById(Integer id);
	public List<Cart> findByUserId(Integer userId);
	public Cart findByUserIdAndProductId(Integer userId, Integer productId);
	
	public Cart saveCart(Cart cart);
	public void deleteCart(Integer id);

	public List<Cart> getCartByUser(User user);
	
	
	public void updateItemsCartIncreaseQuatity(User user,Product product, int quatity);
	public void updateItemsCartChangeQuatity(User user , Product product, int quatity);
	public void addNewItem(User user, Product product, int quatity);
	public void deleteItems(User user, Product product);
	public void deleteCartAfterBuy(User user);
}
