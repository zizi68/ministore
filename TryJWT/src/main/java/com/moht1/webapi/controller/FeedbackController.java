package com.moht1.webapi.controller;

import com.moht1.webapi.Exception.AppUtils;
import com.moht1.webapi.model.Feedback;
import com.moht1.webapi.model.OrderDetail;
import com.moht1.webapi.service.FeedbackService;
import com.moht1.webapi.service.OrderDetailService;
import com.moht1.webapi.service.ProductService;
import com.moht1.webapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

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
    public ResponseEntity<List<Feedback>> getFeedbacksByProductId(@RequestParam("productId") int productId) {
        List<Feedback> list = feedbackService.getFeedbacksByProductId(productId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{orderDetailId}")
    public ResponseEntity<Feedback> getFeedbackByOrderDetailId(@PathVariable("orderDetailId") int orderDetailId) {
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
