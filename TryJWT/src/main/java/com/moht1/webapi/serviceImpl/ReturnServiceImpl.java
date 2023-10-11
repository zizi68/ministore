package com.moht1.webapi.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.moht1.webapi.model.Order;
import com.moht1.webapi.model.Return;
import com.moht1.webapi.model.ReturnDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moht1.webapi.repository.OrderRepository;
import com.moht1.webapi.repository.OrderStatusRepository;
import com.moht1.webapi.repository.ReturnDetailRepository;
import com.moht1.webapi.repository.ReturnRepository;
import com.moht1.webapi.service.ReturnService;

@Service
public class ReturnServiceImpl implements ReturnService {

	@Autowired
	ReturnRepository returnRepository;

	@Autowired
	ReturnDetailRepository returnDetailRepository;
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	OrderStatusRepository orderStatusRepository;

	@Override
	public Return findByReturnId(Integer id) {
		Optional<Return> return0 = returnRepository.findById(id);
		if(!return0.isPresent()) {
			return null;
		}
		return return0.get();
	}

	@Override
	public List<Return> findByIsApprovedOrderByDateDesc(Boolean isApproved) {
		// TODO Auto-generated method stub
		return returnRepository.findByIsApprovedOrderByDateDesc(isApproved);
	}

	@Override
	public List<ReturnDetail> findReturnDetailByReturnId(Integer returnId) {
		Return return0 = returnRepository.getById(returnId);
		List<ReturnDetail> list = returnDetailRepository.findByReturn0(return0);
		return list;
	}
	
	@Override
	public Return findReturnByOrderId(Integer orderId) {
		Order order = orderRepository.getById(orderId);
		Return return0 = returnRepository.findByOrder(order).get(0);
		return return0;
	}

	@Override
	public Return updateReturn(Return return0, Boolean isApproved) {
		return0.setIsApproved(isApproved);
		return returnRepository.save(return0);
	}

	@Override
	public List<ReturnDetail> saveListReturnDetail(List<ReturnDetail> returnDetails) {
		// TODO Auto-generated method stub
		return returnDetailRepository.saveAll(returnDetails);
	}

	@Override
	public ReturnDetail findByReturnDetailId(Integer id) {
		Optional<ReturnDetail> return0 = returnDetailRepository.findById(id);
		if(!return0.isPresent()) {
			return null;
		}
		return return0.get();
	}

	@Override
	public List<Return> getAllByUserId(Integer userId) {
		// TODO Auto-generated method stub
		List<Return> list = returnRepository.findAllByOrderByIdDesc();
		List<Return> result = new ArrayList<>();
		for(Return r : list) {
			if(r.getOrder().getUser().getId() == userId)
				result.add(r);
		}
		return result;
	}
}
