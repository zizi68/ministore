package com.trinh.webapi.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trinh.webapi.model.Order;
import com.trinh.webapi.model.OrderDetail;
import com.trinh.webapi.model.OrderStatus;
import com.trinh.webapi.model.Return;
import com.trinh.webapi.model.ReturnDetail;
import com.trinh.webapi.model.User;
import com.trinh.webapi.repository.OrderDetailRepository;
import com.trinh.webapi.repository.OrderRepository;
import com.trinh.webapi.repository.OrderStatusRepository;
import com.trinh.webapi.repository.ReturnDetailRepository;
import com.trinh.webapi.repository.ReturnRepository;
import com.trinh.webapi.service.OrderService;
import com.trinh.webapi.service.ReturnService;

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
