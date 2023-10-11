package com.moht1.webapi.service;

import java.util.List;

import com.moht1.webapi.model.Return;
import com.moht1.webapi.model.ReturnDetail;

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
