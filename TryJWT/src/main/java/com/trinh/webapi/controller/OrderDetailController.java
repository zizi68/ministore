package com.trinh.webapi.controller;

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
import com.trinh.webapi.Exception.NotFoundException;
import com.trinh.webapi.model.Order;
import com.trinh.webapi.model.OrderDetail;
import com.trinh.webapi.service.OrderDetailService;
import com.trinh.webapi.service.OrderService;
import com.trinh.webapi.service.ProductService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/order-detail")
public class OrderDetailController {

	@Autowired
	OrderService orderService;
	
	@Autowired
	OrderDetailService orderDetailService;
	
	@Autowired
	ProductService productService;
	
	@PostMapping(value = "/{id}")
	public ResponseEntity<?> insertOrderDetailByOrderId(@PathVariable("id") Integer id, @RequestBody List<OrderDetail> orderDetails) {
		Order order = orderService.findById(id);
		if (order == null) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Order not found!", null);
		}
		for(OrderDetail o : orderDetails)
			o.setOrder(order);
		
		if (orderDetailService.updateSoldQuantityByOrderDetail(orderDetails, 1) == false)
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Order containing product have been sold out", null);
		
		orderDetailService.saveListOrderDetail(orderDetails);
		return AppUtils.returnJS(HttpStatus.OK, "Save order details successfully!", null);
	}
	
	
}
