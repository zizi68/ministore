package com.moht1.webapi.repository;

import com.moht1.webapi.model.Feedback;
import com.moht1.webapi.model.FeedbackId;
import com.moht1.webapi.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, FeedbackId> {
    //	List<Feedback> findAllByProduct(Product product);
//	public Feedback findByUserAndProduct(User user, Product product);
    public Feedback findByOrderDetail(OrderDetail orderDetail);

}