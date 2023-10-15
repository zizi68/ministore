package com.moht1.webapi.controller;


import com.moht1.webapi.repository.OrderDetailRepository;
import com.moht1.webapi.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
