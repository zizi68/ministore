package com.moht1.webapi.serviceImpl;

import com.moht1.webapi.model.Feedback;
import com.moht1.webapi.model.FeedbackId;
import com.moht1.webapi.model.OrderDetail;
import com.moht1.webapi.model.Product;
import com.moht1.webapi.repository.FeedbackRepository;
import com.moht1.webapi.repository.OrderDetailRepository;
import com.moht1.webapi.repository.ProductRepository;
import com.moht1.webapi.repository.UserRepository;
import com.moht1.webapi.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        for (OrderDetail o : details) {
            Feedback f = feedbackRepository.findByOrderDetail(o);
            if (f != null)
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
        if (!order.isPresent())
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
