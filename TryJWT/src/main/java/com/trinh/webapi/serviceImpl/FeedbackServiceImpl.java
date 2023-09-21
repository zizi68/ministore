package com.trinh.webapi.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trinh.webapi.model.Feedback;
import com.trinh.webapi.model.FeedbackId;
import com.trinh.webapi.model.OrderDetail;
import com.trinh.webapi.model.Product;
import com.trinh.webapi.model.User;
import com.trinh.webapi.repository.FeedbackRepository;
import com.trinh.webapi.repository.OrderDetailRepository;
import com.trinh.webapi.repository.ProductRepository;
import com.trinh.webapi.repository.UserRepository;
import com.trinh.webapi.service.FeedbackService;

@Service
public class FeedbackServiceImpl implements FeedbackService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	FeedbackRepository feedbackRepository;
	
	@Autowired
	OrderDetailRepository orderDetailRepository;


	@Override
	public List<Feedback> getFeedbacksByProductId(Integer productId) {
		Product product = productRepository.getById(productId);
		List<OrderDetail> details = orderDetailRepository.findByProduct(product);
		List<Feedback> feedbacks = new ArrayList<>();
		for(OrderDetail o : details) {
			Feedback f = feedbackRepository.findByOrderDetail(o);
			if(f != null)
				feedbacks.add(f);
		}
		return feedbacks;
	}

	@Override
	public Feedback getFeedbackByOrderDetailId(Integer orderDetailId) {
//		User user = userRepository.getById(userId);
//		Product product = productRepository.getById(productId);
//		Feedback feedback = feedbackRepository.findByUserAndProduct(user, product);
//		return feedback;
		Optional<OrderDetail> order = orderDetailRepository.findById(orderDetailId);
		if(!order.isPresent()) 
			return null;
		
		Feedback f = feedbackRepository.findByOrderDetail(order.get());
		return f;
	}

	@Override
	public Feedback saveFeedback(Feedback feedback) {
		return feedbackRepository.save(feedback);
	}

	@Override
	public void deleteFeedback(FeedbackId feedbackId) {
		feedbackRepository.deleteById(feedbackId);
	}

}
