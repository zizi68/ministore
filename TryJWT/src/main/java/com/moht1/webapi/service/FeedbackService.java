package com.moht1.webapi.service;

import com.moht1.webapi.model.Feedback;
import com.moht1.webapi.model.FeedbackId;

import java.util.List;

public interface FeedbackService {
    public List<Feedback> getFeedbacksByProductId(Integer productId);

    public Feedback getFeedbackByOrderDetailId(Integer orderDetailId);

    public Feedback saveFeedback(Feedback feedback);

    public void deleteFeedback(FeedbackId feedbackId);
}
