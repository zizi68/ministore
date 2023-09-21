package com.trinh.webapi.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trinh.webapi.Exception.AppUtils;
import com.trinh.webapi.model.Feedback;
import com.trinh.webapi.model.OrderDetail;
import com.trinh.webapi.service.FeedbackService;
import com.trinh.webapi.service.OrderDetailService;
import com.trinh.webapi.service.ProductService;
import com.trinh.webapi.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/feedbacks")
public class FeedbackController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	FeedbackService feedbackService;
	
	@Autowired
	OrderDetailService orderDetailService;
	
	@GetMapping("")
	public ResponseEntity<List<Feedback>> getFeedbacksByProductId(@RequestParam("productId") int productId) 
	{
		List<Feedback> list = feedbackService.getFeedbacksByProductId(productId);
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/{orderDetailId}")
	public ResponseEntity<Feedback> getFeedbackByOrderDetailId(@PathVariable("orderDetailId") int orderDetailId) 
	{
		Feedback feedback = feedbackService.getFeedbackByOrderDetailId(orderDetailId);
		
		return ResponseEntity.ok(feedback);
	}
	
	@PostMapping("/{orderDetailId}")
	public ResponseEntity<?> insertFeedback(@PathVariable("orderDetailId") Integer orderDetailId, @RequestBody Feedback feedback) {
//		Integer userId = feedback.getId().getUserId();
//		User user = userService.findById(userId);
//		if (user == null) {
//			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "User not found!", null);
//		}
		
//		Integer productId = feedback.getId().getProductId(); 
//		Product product = productService.findById(productId);
//		if (product == null) {
//			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Product not found!", null);
//		}
		
//		Integer productId = feedback.getOrderDetail().getId();
		OrderDetail detail = orderDetailService.findById(orderDetailId);
		if (detail == null) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Order detail not found!", null);
		}
		
		feedback.setDate(new Date());
		feedback.setOrderDetail(detail);
		feedback = feedbackService.saveFeedback(feedback);

		return AppUtils.returnJS(HttpStatus.OK, "Save feedback successfully!", feedback);
	}
}
