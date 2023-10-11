package com.moht1.webapi.controller;

import java.util.List;

import com.moht1.webapi.model.Order;
import com.moht1.webapi.model.OrderDetail;
import com.moht1.webapi.service.OrderDetailService;
import com.moht1.webapi.service.OrderService;
import com.moht1.webapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moht1.webapi.Exception.AppUtils;

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
