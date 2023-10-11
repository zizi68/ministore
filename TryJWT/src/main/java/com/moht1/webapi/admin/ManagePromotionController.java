package com.moht1.webapi.admin;

import java.util.List;

import com.moht1.webapi.model.Product;
import com.moht1.webapi.model.Promotion;
import com.moht1.webapi.model.PromotionDetail;
import com.moht1.webapi.model.User;
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

import com.moht1.webapi.Exception.AppUtils;
import com.moht1.webapi.Exception.NotFoundException;
import com.moht1.webapi.service.ProductService;
import com.moht1.webapi.service.PromotionDetailService;
import com.moht1.webapi.service.PromotionService;
import com.moht1.webapi.service.UserService;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin/promotion")
public class ManagePromotionController {
	
	@Autowired
	PromotionService promotionService;
	
	@Autowired
	PromotionDetailService promotionDetailService;	
	
	@Autowired
	ProductService productService;
	
	@Autowired
	UserService userService;
	
	@GetMapping(value = "/status/{status}")
	public ResponseEntity<?> getAllPromotion(@PathVariable("status") String status) {
		List<Promotion> list = promotionService.findAllByStatus(status);
		
		return ResponseEntity.ok(list);
	}
	
	@GetMapping(value = { "/{id}" })
	public ResponseEntity<?> getPromotionById(@PathVariable("id") Integer id) {
		Promotion promotion = null;
		promotion = promotionService.findById(id);
		if (promotion == null)
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Promotion is unavaiable", null);
		return ResponseEntity.ok(promotion);
	}
	
	@GetMapping(value = { "/search/{startDate}/{finishDate}" })
	public ResponseEntity<?> searchPromotion(@PathVariable("startDate") String startDate, @PathVariable("finishDate") String finishDate) {
		
		return ResponseEntity.ok(promotionService.findPromotion(
				promotionService.convertStringToDate(startDate),
				promotionService.convertStringToDate(finishDate)));
	}
	
	@GetMapping(value = { "/{productId}/{startDate}/{finishDate}" })
	public ResponseEntity<?> checkPromotionByProductId(@PathVariable("productId") Integer productId,
			@PathVariable("startDate") String startDate,
			@PathVariable("finishDate") String finishDate) {
		
		Product product = productService.findById(productId);
		if (product == null) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Product not found!", null);
		}

		String result = promotionService.checkPromotionByProduct(product,
				promotionService.convertStringToDate(startDate),
				promotionService.convertStringToDate(finishDate));
		
		return AppUtils.returnJS(HttpStatus.OK, result, null);
	}
	
	@GetMapping(value = "/promotion-detail")
	public ResponseEntity<?> getPromotionDetailByPromotionId(@RequestParam("promotionId") Integer promotionId) {
		List<PromotionDetail> list = null;
		try {
			list = promotionDetailService.findAllByPromotionId(promotionId);
		} catch (NotFoundException e) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Promotion is unavaiable", null);
		}
		return ResponseEntity.ok(list);
	}
	
	@PostMapping(value = "/{userId}/{startDate}/{finishDate}")
	public ResponseEntity<?> insertPromotionByUserId(@PathVariable("userId") Integer userId, 
			@PathVariable("startDate") String startDate,
			@PathVariable("finishDate") String finishDate,
			@RequestBody List<PromotionDetail> list) {
		User user = userService.findById(userId);
		if (user == null) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "User not found!", null);
		}
		
		Product product = null;
		String result = "";
		for(PromotionDetail s : list) {
			product = productService.findById(s.getProduct().getId());
			if (product == null) {
				return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Product not found!", null);
			}
			result = promotionService.checkPromotionByProduct(product,
					promotionService.convertStringToDate(startDate),
					promotionService.convertStringToDate(finishDate));
			
			if(result.equals("true"))
				return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Product with ID " + product.getId() + " was in another promotion!", null);
		}
		
		Promotion promotion = new Promotion();
		promotion.setUser(user);
		promotion.setStartDate(promotionService.convertStringToDate(startDate));
		promotion.setFinishDate(promotionService.convertStringToDate(finishDate));
		promotionService.addPromotion(promotion);	
		
//		Product product = null;

		for(PromotionDetail s : list) 
			s.setPromotion(promotion);
		
		promotionDetailService.saveListPromotionDetail(list);
		
		return AppUtils.returnJS(HttpStatus.OK, "Save promotion successfully!", null);
	}
	
	@PutMapping(value = "/{promotionId}/{startDate}/{finishDate}")
	public ResponseEntity<?> updatePromotionByUserId(@PathVariable("promotionId") Integer promotionId, 
			@PathVariable("startDate") String startDate,
			@PathVariable("finishDate") String finishDate,
			@RequestBody List<PromotionDetail> list) {
		Promotion promotion = promotionService.findById(promotionId);
		if (promotion == null) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Promotion not found!", null);
		}
		
		promotion.setStartDate(promotionService.convertStringToDate(startDate));
		promotion.setFinishDate(promotionService.convertStringToDate(finishDate));
		promotionService.editPromotion(promotion);	
		
		Product product = null;
		
		for(PromotionDetail s : list)  {
			s.setPromotion(promotion);
			product = productService.findById(s.getProduct().getId());
			s.setProduct(product);
		}
		
		promotionDetailService.updateListPromotionDetail(promotionId, list);
		
		return AppUtils.returnJS(HttpStatus.OK, "Update promotion successfully!", null);
	}
	
	@DeleteMapping(value = "/{promotionId}")
	public ResponseEntity<?> deletePromotionByPromotionId(@PathVariable("promotionId") Integer promotionId) {
		Promotion promotion = promotionService.findById(promotionId);
		if (promotion == null) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Promotion not found!", null);
		}
		
		promotionService.deletePromotion(promotion);	
		
		return AppUtils.returnJS(HttpStatus.OK, "Delete promotion successfully!", null);
	}
	
	@GetMapping(value = "/product/{productId}")
	public ResponseEntity<?> getPromotionByProductId(@PathVariable("productId") Integer productId) {
		Product product = productService.findById(productId);
		if (product == null) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Product not found!", null);
		}
		
		int result = promotionService.getCurrentPromotionByProduct(product);	
		
		return AppUtils.returnJS(HttpStatus.OK, result + "", null);
	}
	
}
