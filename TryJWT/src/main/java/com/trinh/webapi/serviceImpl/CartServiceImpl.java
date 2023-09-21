package com.trinh.webapi.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trinh.webapi.dto.FullCart;
import com.trinh.webapi.dto.FullProduct;
import com.trinh.webapi.model.Cart;
import com.trinh.webapi.model.Product;
import com.trinh.webapi.model.User;
import com.trinh.webapi.repository.CartRepository;
import com.trinh.webapi.repository.ProductRepository;
import com.trinh.webapi.repository.UserRepository;
import com.trinh.webapi.service.CartService;


@Service
public class CartServiceImpl implements CartService {
	
	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ProductRepository productRepository;

	@Override
	public List<Cart> findByUserId(Integer userId) {
		List<Cart> list = cartRepository.findByUserId(userId);
		
		return list;
	}
	
	@Override
	public Cart findByUserIdAndProductId(Integer userId, Integer productId) {
		User user = userRepository.getById(userId);
		Product product = productRepository.getById(productId);
		return cartRepository.findByUserAndProduct(user, product);
	}

	@Override
	public Cart saveCart(Cart cart) {
		return cartRepository.save(cart);
	}

	@Override
	@Transactional
	public void deleteCart(Integer id) {
		
		cartRepository.deleteById(id);
	}

	@Override
	public Cart findById(Integer id) {
		Optional<Cart> cartObject = cartRepository.findById(id);
		if(!cartObject.isPresent()) {
			return null; 
		}
		return cartObject.get();
	}

	@Override
	public List<Cart> getCartByUser(User user) {
		// TODO Auto-generated method stub
		return cartRepository.findByUser(user);
	}

	@Override
	public void updateItemsCartIncreaseQuatity(User user, Product product, int quatity) {
		Cart cart = cartRepository.findByUserAndProduct(user, product);
		
		cart.setQuantity(cart.getQuantity()+quatity);
		cartRepository.save(cart);
		
	}

	@Override
	public void updateItemsCartChangeQuatity(User user, Product product, int quatity) {
		Cart cart = cartRepository.findByUserAndProduct(user, product);
		
		cart.setQuantity(quatity);
		cartRepository.save(cart);
		
	}

	@Override
	public void addNewItem(User user, Product product, int quatity) {
		Cart cart = new Cart();
		cart.setProduct(product);
		cart.setUser(user);
		cart.setQuantity(quatity);
		cartRepository.save(cart);
		
		
	}

	@Override
	public void deleteItems(User user, Product product) {
		Cart cart = cartRepository.findByUserAndProduct(user, product);
		cartRepository.delete(cart);
		
	}

	@Override
	public void deleteCartAfterBuy(User user) {
		
		cartRepository.deleteByUser(user);
			
	}

}
