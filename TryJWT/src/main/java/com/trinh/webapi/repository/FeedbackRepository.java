package com.trinh.webapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trinh.webapi.model.Feedback;
import com.trinh.webapi.model.FeedbackId;
import com.trinh.webapi.model.OrderDetail;
import com.trinh.webapi.model.Product;
import com.trinh.webapi.model.User;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, FeedbackId>{
//	List<Feedback> findAllByProduct(Product product);
//	public Feedback findByUserAndProduct(User user, Product product);
	public Feedback findByOrderDetail(OrderDetail orderDetail);
	
}