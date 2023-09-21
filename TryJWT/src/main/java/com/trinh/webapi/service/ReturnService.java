package com.trinh.webapi.service;

import java.util.List;

import com.trinh.webapi.model.Order;
import com.trinh.webapi.model.OrderDetail;
import com.trinh.webapi.model.OrderStatus;
import com.trinh.webapi.model.Return;
import com.trinh.webapi.model.ReturnDetail;
import com.trinh.webapi.model.User;

public interface ReturnService {

	public Return findByReturnId(Integer id);
	public List<Return> findByIsApprovedOrderByDateDesc(Boolean isApproved);
	public List<ReturnDetail> findReturnDetailByReturnId(Integer returnId);
	public Return findReturnByOrderId(Integer orderId);
	public Return updateReturn(Return return0, Boolean isApproved);
	public List<Return> getAllByUserId(Integer userId);
	public List<ReturnDetail> saveListReturnDetail(List<ReturnDetail> returnDetails);
	public ReturnDetail findByReturnDetailId(Integer id);
	
}
