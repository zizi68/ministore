package com.moht1.webapi.service;

import com.moht1.webapi.model.Return;
import com.moht1.webapi.model.ReturnDetail;

import java.util.List;

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
