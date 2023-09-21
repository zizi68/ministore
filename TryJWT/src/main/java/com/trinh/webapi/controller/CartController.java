package com.trinh.webapi.controller;


import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trinh.webapi.Exception.AppUtils;
import com.trinh.webapi.Exception.NotFoundException;
import com.trinh.webapi.dto.FullCart;
import com.trinh.webapi.dto.FullProduct;
import com.trinh.webapi.model.Cart;
import com.trinh.webapi.model.CartSupport;
import com.trinh.webapi.model.Product;
import com.trinh.webapi.model.User;
import com.trinh.webapi.payload.request.CartRequest;
import com.trinh.webapi.payload.response.ResponseBody;
import com.trinh.webapi.service.CartService;
import com.trinh.webapi.service.PriceHistoryService;
import com.trinh.webapi.service.ProductService;
import com.trinh.webapi.service.PromotionService;
import com.trinh.webapi.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/cart")
public class CartController {

	@Autowired
	ProductService productService;
	
	@Autowired
	CartService cartService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	PriceHistoryService priceHistoryService;
	
	@Autowired
	PromotionService promotionService;
	

	@PostMapping("/add-to-cart")
	public ResponseEntity<?> addToCart(HttpSession session, @RequestParam Integer id, @RequestParam Optional<Integer> quantity,
			Principal principal) {
		Product product = null;
		
		try {
			product = productService.getProductById(id);
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		String username = principal.getName();
		User user = userService.getUserByUsername(username);
		int quantityofProduct = quantity.orElse(1);
		CartSupport cartSupport = new CartSupport(cartService.getCartByUser(user));

		int action = cartSupport.addItem(product, quantityofProduct); // 1: update 2:insert

		if (action == 1 && principal != null) {
			
			cartService.updateItemsCartIncreaseQuatity(user, product, quantityofProduct);

		} else if (action == 2 && principal != null) {

			
			cartService.addNewItem(user, product, quantityofProduct);

		}
		
		return AppUtils.returnJS(HttpStatus.OK, "Add cart successfully!", null);
	}

	@PostMapping("/update-cart")
	public ResponseEntity<?> updateCart(HttpSession session, @RequestParam Integer id, @RequestParam Optional<Integer> quantity,
			Principal principal) {
		Product product = null;
		try {
			product = productService.getProductById(id);
		} catch (NotFoundException e) {
			e.printStackTrace();

		}
		String username = principal.getName();
		User user = userService.getUserByUsername(username);
		int quantityofProduct = quantity.orElse(1);
		int maximumQuantity = product.getQuantity();
		if (quantityofProduct > maximumQuantity) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, 
					"The requested quantity exceeds the remaining quantity of this product!", null);
		}
		CartSupport cartSupport = new CartSupport(cartService.getCartByUser(user));
		cartSupport.updateItem(product, quantityofProduct);

		if (principal != null) {

			cartService.updateItemsCartChangeQuatity(user, product, quantityofProduct);
		}
		
		return AppUtils.returnJS(HttpStatus.OK, "Update cart successfully!", null);
	}

	@PostMapping("/remove-item")
	public ResponseEntity<?> removeItem(HttpSession session, @RequestParam Integer id, Principal principal) {
		Product product = null;
		try {
			product = productService.getProductById(id);
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		String username = principal.getName();
		User user = userService.getUserByUsername(username);
		
		
		
		CartSupport cartSupport = new CartSupport(cartService.getCartByUser(user));
		
		if (principal != null) {
			
			cartService.deleteItems(user, product);
		}
		return AppUtils.returnJS(HttpStatus.OK, "Remove cart successfully!", null);
	}

	@GetMapping("")
	public ResponseEntity<List<FullCart>> getCartByUserId(@RequestParam("userId") int userId) 
	{
		List<Cart> list = cartService.findByUserId(userId);
		
		List<FullCart> result = new ArrayList<>();
		
		FullCart fc = null;
		FullProduct p = null;
		
		for(Cart c : list) {
			p = new FullProduct(c.getProduct(), priceHistoryService.getLatestPrice(c.getProduct()), 
					promotionService.getCurrentPromotionByProduct(c.getProduct()));
			
			fc = new FullCart(c.getId(), p, c.getUser(), c.getQuantity());
			result.add(fc);
		}
		return ResponseEntity.ok(result);
	}
	
	@PostMapping
	public ResponseEntity<?> postCart(@RequestBody CartRequest cartRequest) {
		Product product = productService.findById(cartRequest.getProductId());
		
		if (product == null) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Product not found!", null);
		}
		User user = userService.findById(cartRequest.getUserId());
		if (user == null) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "User not found!", null);
		}
		int quantity = cartRequest.getQuantity();
		
//		if (quantity <= 0 ) 
//			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Quantity of cart item must be greater than 0!!!", null);
		
		Cart cart = cartService.findByUserIdAndProductId(cartRequest.getUserId(), cartRequest.getProductId());
		
		if (cart == null) {
			cart = new Cart(null, product, user, quantity);
		}
		else {
			int afterQuantity = cart.getQuantity() + quantity;
			afterQuantity = (afterQuantity <= 0) ? 0 : afterQuantity;
			if (afterQuantity > (cart.getProduct().getQuantity() - cart.getProduct().getSoldQuantity())) {
				return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "The remaining quantity of this product is not enough!", null);
			}
			cart.setQuantity(afterQuantity);
		}
		cart = cartService.saveCart(cart);
		FullProduct p = new FullProduct(cart.getProduct(), priceHistoryService.getLatestPrice(cart.getProduct()), 
				promotionService.getCurrentPromotionByProduct(cart.getProduct()));
		FullCart result = new FullCart(cart.getId(), p, cart.getUser(), cart.getQuantity());
		return AppUtils.returnJS(HttpStatus.OK, "Add cart successfully!", result);
	}
	
	@PutMapping
	public ResponseEntity<?> putCart(@RequestBody CartRequest cartRequest) {
		Product product = productService.findById(cartRequest.getProductId());
		if (product == null) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Product not found!", null);
		}
		User user = userService.findById(cartRequest.getUserId());
		if (user == null) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "User not found!", null);
		}
		int quantity = cartRequest.getQuantity();
		
		Cart cart = cartService.findByUserIdAndProductId(cartRequest.getUserId(), cartRequest.getProductId());
//		
		if (quantity <= 0 ) {
//			User user = userService.findById(cart.getUser().getId());
			user.getCarts().remove(cart);
			
//			Product product = productService.findById(cart.getProduct().getId());
			product.getCarts().remove(cart);
			
			userService.saveUser(user);
			productService.addProduct(product);		
			
			cartService.deleteCart(cart.getId());
			cart.setQuantity(0);
			return AppUtils.returnJS(HttpStatus.OK, "Delete cart successfully!", cart);
		}
		
		if (cart == null) {
			cart = new Cart(null, product, user, quantity);
		}
		else {
			int productQuantity = cart.getProduct().getQuantity() - cart.getProduct().getSoldQuantity();
			if (quantity > productQuantity) {
				cart.setQuantity(productQuantity);
				cart = cartService.saveCart(cart);
				return AppUtils.returnJS(HttpStatus.BAD_REQUEST, 
						"There are only " + productQuantity + " quantity remaining for this item!", cart);
			}			
		}
		cart.setQuantity(quantity);
		cart = cartService.saveCart(cart);
		FullProduct p = new FullProduct(cart.getProduct(), priceHistoryService.getLatestPrice(cart.getProduct()), 
				promotionService.getCurrentPromotionByProduct(cart.getProduct()));
		FullCart result = new FullCart(cart.getId(), p, cart.getUser(), cart.getQuantity());
		return AppUtils.returnJS(HttpStatus.OK, "Edit cart successfully!", result);
	}
	

	// NOT WORKING!!! -> worked
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCart(@PathVariable("id") Integer id) {
		Cart cart = cartService.findById(id);
		if (cart == null) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Cart item not found!", null);
		}
		
		User user = userService.findById(cart.getUser().getId());
		user.getCarts().remove(cart);
		
		Product product = productService.findById(cart.getProduct().getId());
		product.getCarts().remove(cart);
		
		userService.saveUser(user);
		productService.addProduct(product);		
		
		cartService.deleteCart(id);		
		return AppUtils.returnJS(HttpStatus.OK, "Delete cart item successfully!", null);
	}


	// NOT WORKING!!!
//	@DeleteMapping("/{id}")
//	public ResponseEntity<?> deleteCart(@PathVariable("id") long id) {
//		Cart cart = cartService.findById(id);
//		if (cart == null) {
//			return ResponseEntity
//					.status(HttpStatus.BAD_REQUEST)
//	                .body(new ResponseBody(400, "Cart item not found!", null));
//		}
//		cartService.deleteCart(id);		
//		return ResponseEntity
//				.status(HttpStatus.OK)
//                .body(new ResponseBody(200, "Delete cart item successfully!", cart));
//	}

}
