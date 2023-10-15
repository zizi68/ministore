package com.moht1.webapi.serviceImpl;

import com.moht1.webapi.model.OrderStatus;
import com.moht1.webapi.repository.OrderDetailRepository;
import com.moht1.webapi.repository.OrderRepository;
import com.moht1.webapi.repository.OrderStatusRepository;
import com.moht1.webapi.service.OrderStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderStatusServiceImpl implements OrderStatusService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderStatusRepository orderStatusRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;


    @Override
    public OrderStatus findById(Integer id) {
        Optional<OrderStatus> orderStatusOptional = orderStatusRepository.findById(id);
        if (!orderStatusOptional.isPresent()) {
            return null;
        }
        return orderStatusOptional.get();
    }

}
