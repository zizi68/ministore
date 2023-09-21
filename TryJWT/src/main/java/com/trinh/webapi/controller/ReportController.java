package com.trinh.webapi.controller;


import java.security.Principal;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.trinh.webapi.dto.OrderDTO;
import com.trinh.webapi.dto.OrderSummaryDTO;
import com.trinh.webapi.model.Cart;
import com.trinh.webapi.model.CartSupport;
import com.trinh.webapi.model.Order;
import com.trinh.webapi.model.OrderDetail;
import com.trinh.webapi.model.OrderStatus;
import com.trinh.webapi.model.Product;
import com.trinh.webapi.model.User;
import com.trinh.webapi.payload.response.ResponseBody;
import com.trinh.webapi.repository.OrderDetailRepository;
import com.trinh.webapi.service.CartService;
import com.trinh.webapi.service.OrderDetailService;
import com.trinh.webapi.service.OrderService;
import com.trinh.webapi.service.OrderStatusService;
import com.trinh.webapi.service.PriceHistoryService;
import com.trinh.webapi.service.ProductService;
import com.trinh.webapi.service.ReportService;
import com.trinh.webapi.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/statistics")
public class ReportController {

	@Autowired
	OrderService orderService;

	@Autowired
	CartService cartService;
	
	@Autowired
	OrderStatusService orderStatusService;
	
	@Autowired
	OrderDetailService orderDetailService;
  
	@Autowired
 	OrderDetailRepository detailRepository;	
	
	@Autowired
	ProductService productService;

	@Autowired
	UserService userService;

	@Autowired
	ReportService reportService;	
	
	@Autowired
	PriceHistoryService priceHistoryService;
	
	@GetMapping(value = "/month/{year}")
	public ResponseEntity<?> getReportByMonth(@PathVariable("year") Integer year) {
		return ResponseEntity.ok(reportService.reportRevenueByMonth(year));
	}
	
	@GetMapping(value = "/year/{year1}/{year2}")
	public ResponseEntity<?> getReportByYear(@PathVariable("year1") Integer year1, @PathVariable("year2") Integer year2) {
		return ResponseEntity.ok(reportService.reportRevenueByYear(year1, year2));
	}
	
	@GetMapping(value = "/date/{date1}/{date2}")
	public ResponseEntity<?> getReportByDate(@PathVariable("date1") String date1, @PathVariable("date2") String date2) {
		return ResponseEntity.ok(reportService.reportRevenueByDate(date1, date2));
	}
}
