package com.moht1.webapi.controller;


import com.moht1.webapi.Exception.AppUtils;
import com.moht1.webapi.Exception.NotFoundException;
import com.moht1.webapi.dto.ReturnSummaryDTO;
import com.moht1.webapi.model.*;
import com.moht1.webapi.repository.OrderDetailRepository;
import com.moht1.webapi.service.*;
import com.moht1.webapi.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/return")
public class ReturnController {

    @Autowired
    OrderService orderService;

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
    ReturnService returnService;

    @GetMapping(value = {"/{id}"})
    public ResponseEntity<?> getReturnById(@PathVariable("id") Integer id) {
        Return return0 = null;
        return0 = returnService.findByReturnId(id);
        if (return0 == null)
            return AppUtils.returnJS(HttpStatus.BAD_REQUEST, Constants.RETURN_404.getMessage(), null);
        return ResponseEntity.ok(return0);
    }

    @GetMapping(value = "/order/{orderId}")
    public ResponseEntity<?> getReturnByOrderId(@PathVariable("orderId") Integer orderId) {
        Return return0 = null;
        try {
            return0 = returnService.findReturnByOrderId(orderId);
        } catch (Exception e) {
            return AppUtils.returnJS(HttpStatus.BAD_REQUEST, Constants.RETURN_404.getMessage(), null);
        }
        return ResponseEntity.ok(return0);
    }

    @GetMapping(value = "/return-detail")
    public ResponseEntity<?> getReturnDetailByReturnId(@RequestParam("returnId") Integer returnId) {
        List<ReturnDetail> list = null;
        try {
            list = returnService.findReturnDetailByReturnId(returnId);
        } catch (NotFoundException e) {
            return AppUtils.returnJS(HttpStatus.BAD_REQUEST, Constants.RETURN_404.getMessage(), null);
        }
        return ResponseEntity.ok(list);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> changeReturnStatus(@PathVariable("id") Integer id, @RequestParam("statusId") Integer statusId) {
        Return return0 = returnService.findByReturnId(id);

        if (return0 == null)
            return AppUtils.returnJS(HttpStatus.BAD_REQUEST, Constants.RETURN_404.getMessage(), null);

        Order order = orderService.findById(return0.getOrder().getId());
        if (statusId == 1) {
            returnService.updateReturn(return0, true);
            orderService.updateOrder(order, 8);
            List<OrderDetail> list = new ArrayList<>();
            OrderDetail temp = null;
            for (ReturnDetail r : return0.getReturnDetails()) {
                temp = new OrderDetail();
                temp.setProduct(r.getProduct());
                temp.setQuantity(r.getQuantity());
                list.add(temp);
            }
            orderDetailService.updateSoldQuantityByOrderDetail(list, 2);
        } else {
            returnService.updateReturn(return0, false);
            orderService.updateOrder(order, 7);
        }

        return AppUtils.returnJS(HttpStatus.OK, Constants.VALIDATION_SUCCESS.getMessage(), return0);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getReturnsByUser(@PathVariable("id") Integer id) {
        User user = userService.findById(id);

        if (user == null) {
            return AppUtils.returnJS(HttpStatus.BAD_REQUEST, Constants.USER_404.getMessage(), null);
        }

        List<Return> list = returnService.getAllByUserId(id);
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/return-summary")
    public ResponseEntity<?> getReturnDetailSummaryByReturnId(@RequestParam("returnId") Integer returnId) {
        List<ReturnDetail> list = null;
        try {
            list = returnService.findReturnDetailByReturnId(returnId);
        } catch (NotFoundException e) {
            return AppUtils.returnJS(HttpStatus.BAD_REQUEST, Constants.RETURN_404.getMessage(), null);
        }
        ReturnSummaryDTO dto = new ReturnSummaryDTO();
        dto.setReturnDetail(list.get(0));
        dto.setSize(list.size());
        return ResponseEntity.ok(dto);
    }

    @PostMapping(value = "/{id}")
    public ResponseEntity<?> insertReturnByOrderId(@PathVariable("id") Integer id, @RequestBody Return return0) {
        Order order = orderService.findById(id);
        if (order == null) {
            return AppUtils.returnJS(HttpStatus.BAD_REQUEST, Constants.ORDER_404.getMessage(), null);
        }
        return0.setDate(new Date());
        return0.setOrder(order);
        return0 = returnService.updateReturn(return0, null);
        orderService.updateOrder(order, 9);
        return AppUtils.returnJS(HttpStatus.OK, Constants.VALIDATION_SUCCESS.getMessage(), return0);
    }

    @PostMapping(value = "/return-detail/{id}")
    public ResponseEntity<?> insertOrderDetailByOrderId(@PathVariable("id") Integer id, @RequestBody List<ReturnDetail> returnDetails) {
        Return return0 = returnService.findByReturnId(id);
        if (return0 == null) {
            return AppUtils.returnJS(HttpStatus.BAD_REQUEST, Constants.RETURN_404.getMessage(), null);
        }
        for (ReturnDetail d : returnDetails)
            d.setReturn0(return0);

        returnService.saveListReturnDetail(returnDetails);
        return AppUtils.returnJS(HttpStatus.OK, Constants.VALIDATION_SUCCESS.getMessage(), null);
    }
}
