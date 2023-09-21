package com.ministore.android.model;

import com.google.android.material.timepicker.TimeFormat;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {

    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("date")
    @Expose
    private Date date;
    @SerializedName("id")
    @Expose
    private int orderId;
    @SerializedName("status")
    @Expose
    private StatusId statusId;
    @SerializedName("totalPrice")
    @Expose
    private double totalPrice;
    @SerializedName("user")
    @Expose
    private User user;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public StatusId getStatusId() {
        return statusId;
    }

    public void setStatusId(StatusId statusId) {
        this.statusId = statusId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}