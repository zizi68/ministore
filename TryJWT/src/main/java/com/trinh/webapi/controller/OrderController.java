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
import com.trinh.webapi.model.Return;
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
import com.trinh.webapi.service.ReturnService;
import com.trinh.webapi.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/order")
public class OrderController {

	@Autowired
	OrderService orderService;
	
	@Autowired
	ReturnService returnService;

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

	@GetMapping(value = { "/{id}" })
	public ResponseEntity<?> getOrderById(@PathVariable("id") Integer id) {
		Order order = null;
		order = orderService.findById(id);
		if (order == null)
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Order is unavaiable", null);
		return ResponseEntity.ok(order);
	}

	@GetMapping
	public ResponseEntity<?> getOrderByStatus(@RequestParam("statusId") Integer statusId) {
		List<Order> order = null;
		
		try {
			order = orderService.findByStatusOrderByDateDesc(orderStatusService.findById(statusId));
		} catch (NotFoundException e) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Order is unavaiable", null);
		}
		return ResponseEntity.ok(order);
	}

	@GetMapping(value = "/order-detail")
	public ResponseEntity<?> getOrderDetailById(@RequestParam("orderId") Integer orderId) {
		List<OrderDetail> list = null;
		try {
			list = orderService.findOrderDetailByOrderId(orderId);
		} catch (NotFoundException e) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Order is unavaiable", null);
		}
		return ResponseEntity.ok(list);
	}

	@GetMapping(value = "/report")
	public ResponseEntity<?> getReport() {
		return ResponseEntity.ok(reportService.newReportReceipt(new Date(), 7));
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<?> changeOrderStatus(@PathVariable("id") Integer id,
			@RequestParam("statusId") Integer statusId,
			@RequestParam(value = "shipperId", required = false) Optional<Integer> shipperId,
			@RequestParam(value = "approverId", required = false) Optional<Integer> approverId,
			@RequestParam(value = "payment", required = false) Optional<String> payment) {
		Order order = null;

		
		order = orderService.findById(id);

		if (order == null)
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Order is unavaiable", null);

//		if (statusId == 3)
//			if (orderDetailService.updateSoldQuantityByOrderDetail(order.getOrderDetails(), 1) == false)
//				return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Order containing product have been sold out", null);
		
		if (statusId == 5)
//			if (order.getStatus().getId() == 3)
				orderDetailService.updateSoldQuantityByOrderDetail(order.getOrderDetails(), 2);
		
		
		if (shipperId.isPresent()) {
			User shipper = userService.findById(shipperId.get());
			order.setShipper(shipper);
		}
		if (approverId.isPresent()) {
			User approver = userService.findById(approverId.get());
			order.setApprover(approver);
		}
		if (payment.isPresent()) {
			order.setPaymentType(payment.get());
		}

		orderService.updateOrder(order, statusId);

		return AppUtils.returnJS(HttpStatus.OK, "Update order successfully!", null);
	}

	@GetMapping("/user/{id}")
	public ResponseEntity<?> getOrdersByUserAndStatus(@PathVariable("id") Integer id,
			@RequestParam("statusId") Integer statusId) {
		User user = userService.findById(id);
		if(statusId == 0) {
			List<Order> order = orderService.getAllByUser(user);
			return ResponseEntity.ok(order);
		}
		
		if (user == null) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "User not found!", null);
		}
		OrderStatus orderStatus = orderStatusService.findById(statusId);
		if (orderStatus == null) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Status id not invalid!", null);
		}
		
		List<Order> orders = null;
		
		if(statusId == 9) {
			orders = new ArrayList<>();
			List<Return> returns = returnService.getAllByUserId(id);
			for(Return r : returns)				
				orders.add(r.getOrder());
		}
		else		
			orders = orderService.findByUserAndStatusOrderByDateDesc(user, orderStatus);
		
		return ResponseEntity.ok(orders);
	}

	@GetMapping("/user2/{id}")
	public ResponseEntity<?> getOrdersByUserAndStatus2(@PathVariable("id") Integer id, @RequestParam("statusId") Integer statusId) {
		User user = userService.findById(id);
		if(statusId == 0) {
			
			OrderStatus orderStatus = orderStatusService.findById(statusId);
			List<Order> orders = orderService.getAllByUser(user);
			List<OrderDTO> orders2 = new ArrayList<OrderDTO>();
			for(Order o : orders) {
				OrderDTO  ode = new OrderDTO();
				ode.setOrderId(o.getId());
				ode.setTotalPrice(o.getTotalPrice());
				ode.setAddress(o.getAddress());
				ode.setDate(o.getDate());
				ode.setUser(o.getUser());
				ode.setStatus(o.getStatus());
				ode.setOrderDetails(detailRepository.findByOrder(o));
				
				orders2.add(ode);
			}
			return ResponseEntity.ok(orders2);
		}
		
		if (user == null) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "User not found!", null);
		}
		OrderStatus orderStatus = orderStatusService.findById(statusId);
		if (orderStatus == null) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Status id not invalid!", null);
		}
		List<Order> orders = orderService.findByUserAndStatusOrderByDateDesc(user, orderStatus);
		List<OrderDTO> orders2 = new ArrayList<OrderDTO>();
		for(Order o : orders) {
			OrderDTO  ode = new OrderDTO();
			ode.setOrderId(o.getId());
			ode.setTotalPrice(o.getTotalPrice());
			ode.setAddress(o.getAddress());
			ode.setDate(o.getDate());
			ode.setUser(o.getUser());
			ode.setStatus(o.getStatus());
			ode.setOrderDetails(detailRepository.findByOrder(o));
			
			orders2.add(ode);
		}
		System.err.println(statusId);
		return ResponseEntity.ok(orders2);
	}

	@GetMapping(value = "/order-summary")
	public ResponseEntity<?> getOrderDetailSummaryByOrderId(@RequestParam("orderId") Integer orderId) {
		List<OrderDetail> list = null;
		try {
			list = orderService.findOrderDetailByOrderId(orderId);
		} catch (NotFoundException e) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Order is unavaiable", null);
		}
		OrderSummaryDTO dto = new OrderSummaryDTO();
		dto.setOrderDetail(list.get(0));
		dto.setSize(list.size());
		return ResponseEntity.ok(dto);
	}

	@PostMapping(value = "/{id}")
	public ResponseEntity<?> insertOrderByUserId(@PathVariable("id") Integer id, @RequestBody Order order) {
		User user = userService.findById(id);
		if (user == null) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "User not found!", null);
		}
		order.setDate(new Date());
		order.setUser(user);
//		if(order.getPaymentType() == null)
//			order.setPaymentType("off");

		order = orderService.updateOrder(order, 1);
		return AppUtils.returnJS(HttpStatus.OK, "Save order successfully!", order);
	}
	

	@PostMapping(value = "/add/{id}")
	public ResponseEntity<?> insertOrderByUserId2(@PathVariable("id") Integer id, @RequestBody Order order) {
		User user = userService.findById(id);
		if (user == null) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "User not found!", null);
		}
		order.setDate(new Date());
		order.setUser(user);
		order = orderService.updateOrder(order, 1);
		
		
		List<Cart> listCart = cartService.getCartByUser(user);
		List<OrderDetail> listOrderDetail = new ArrayList<OrderDetail>();
				
				for(Cart s : listCart){
					listOrderDetail.add(new OrderDetail(s.getProduct(),order, s.getQuantity(), priceHistoryService.getLatestPrice(s.getProduct())));
					
					user.getCarts().remove(s);
					
					Product product = productService.findById(s.getProduct().getId());
					product.getCarts().remove(s);
					
					userService.saveUser(user);
					productService.addProduct(product);
					
					cartService.deleteCart(s.getId());		
				}
		orderDetailService.saveListOrderDetail(listOrderDetail);
		
		
		
		return AppUtils.returnJS(HttpStatus.OK, "Save order successfully!", null);
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> addToOrder(HttpSession session, @RequestBody Order order,
			Principal principal) {
		
		String username = principal.getName();
		User user = userService.getUserByUsername(username);
		order.setDate(new Date());
		order.setUser(user);
		order = orderService.updateOrder(order, 1);	
		return AppUtils.returnJS(HttpStatus.OK, "Save order successfully!", order);
		
	}
	@PostMapping(value = "/repurchase/{id}")
	public ResponseEntity<?> repurchaseByOrderId(@PathVariable("id") Integer id) {
		Order order = orderService.findById(id);
		if (order == null) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Order not found!", null);
		}
		User user = userService.findById(order.getUser().getId());
		if (user == null) {
			return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "User not found!", null);
		}
		List<Cart> addCartList = new ArrayList<Cart>();
		List<OrderDetail> orderDetailList = order.getOrderDetails();
		for (OrderDetail orderDetail : orderDetailList) {
			Product product = productService.findById(orderDetail.getProduct().getId());
			if (product == null) {
				// return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Product not found!", null);
				continue;
			}
			int quantity = orderDetail.getQuantity();
			Cart cart = cartService.findByUserIdAndProductId(user.getId(), product.getId());
			if (cart == null) {
				if (quantity > 0 ) {
					cart = new Cart(null, product, user, quantity);
				}
				else {
					// return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Quantity of cart item must be greater than 0!", null);
					continue;
				}
			}
			else {
				int afterQuantity = cart.getQuantity() + quantity;
				afterQuantity = (afterQuantity <= 0) ? 0 : afterQuantity;
				int productQuantity = cart.getProduct().getQuantity() - cart.getProduct().getSoldQuantity();
				if (afterQuantity > productQuantity) {
					// return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "The remaining quantity of this product is not enough!", null);
					afterQuantity = productQuantity;
				}
				cart.setQuantity(afterQuantity);
			}
			cart = cartService.saveCart(cart);
			addCartList.add(cart);
			// return AppUtils.returnJS(HttpStatus.OK, "Add cart successfully!", cart);
		}
		
		return AppUtils.returnJS(HttpStatus.OK, "Add list cart successfully!", addCartList);

	}
}
