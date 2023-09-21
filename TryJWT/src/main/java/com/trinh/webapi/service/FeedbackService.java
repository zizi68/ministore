package com.trinh.webapi.service;

import java.util.List;

import com.trinh.webapi.model.Feedback;
import com.trinh.webapi.model.FeedbackId;

public interface FeedbackService {
	public List<Feedback> getFeedbacksByProductId(Integer productId);
	public Feedback getFeedbackByOrderDetailId(Integer orderDetailId);
	
	public Feedback saveFeedback(Feedback feedback);
	public void deleteFeedback(FeedbackId feedbackId);
}
