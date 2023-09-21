package com.trinh.webapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trinh.webapi.Exception.AppUtils;
import com.trinh.webapi.Exception.NotFoundException;
import com.trinh.webapi.model.Order;
import com.trinh.webapi.service.OrderService;
import com.trinh.webapi.service.OrderStatusService;
import com.trinh.webapi.service.UserService;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/shipper/order")
public class ShipperOrderController {
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	OrderStatusService orderStatusService;
	
	@Autowired
	UserService userService;
	
	@GetMapping
	public ResponseEntity<?> getShipperOrderByStatus(@RequestParam("statusId") Integer statusId, @RequestParam("shipperId") Integer shipperId) {
		List<Order> order = null;
		
		try {
			order = orderService.findByStatusOrderAndShipperByDateDesc(orderStatusService.findById(statusId),  userService.findById(shipperId));
		} catch (NotFoundException e) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Order is unavaiable", null);
		}
		return ResponseEntity.ok(order);
	}
}
