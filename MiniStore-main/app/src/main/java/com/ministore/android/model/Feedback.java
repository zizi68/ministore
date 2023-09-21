package com.ministore.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Feedback {

    @SerializedName("feedbackId")
    @Expose
    private FeedbackId feedbackId;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("product")
    @Expose
    private Product product;
    @SerializedName("date")
    @Expose
    private Date date;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("vote")
    @Expose
    private int vote;

    public FeedbackId getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(FeedbackId feedbackId) {
        this.feedbackId = feedbackId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

}