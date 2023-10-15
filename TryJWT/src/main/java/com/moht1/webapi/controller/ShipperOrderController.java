package com.moht1.webapi.controller;

import com.moht1.webapi.Exception.AppUtils;
import com.moht1.webapi.Exception.NotFoundException;
import com.moht1.webapi.model.Order;
import com.moht1.webapi.service.OrderService;
import com.moht1.webapi.service.OrderStatusService;
import com.moht1.webapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            order = orderService.findByStatusOrderAndShipperByDateDesc(orderStatusService.findById(statusId), userService.findById(shipperId));
        } catch (NotFoundException e) {
            return AppUtils.returnJS(HttpStatus.BAD_REQUEST, "Order is unavaiable", null);
        }
        return ResponseEntity.ok(order);
    }
}
