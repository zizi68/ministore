package com.moht1.webapi.service;

import com.moht1.webapi.model.OrderDetail;

import java.util.List;

public interface OrderDetailService {

    public List<OrderDetail> saveListOrderDetail(List<OrderDetail> orderDetails);

    public boolean updateSoldQuantityByOrderDetail(List<OrderDetail> orderDetails, int type);

    public OrderDetail findById(Integer id);

}
