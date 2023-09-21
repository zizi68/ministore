package com.ministore.android.model;

import com.ministore.android.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrderStatus implements Serializable {
    private int image;
    private Status status;

    private static List<OrderStatus> orderStatusList;

    static {
        orderStatusList = new ArrayList<>();
        orderStatusList.add(new OrderStatus(R.drawable.ic_playlist_add, new Status(1, "Processing")));
//        orderStatusList.add(new OrderStatus(R.drawable.ic_playlist_remove, new Status(2, "Requesting cancellation")));
        orderStatusList.add(new OrderStatus(R.drawable.ic_delivery_dining, new Status(3, "Delivering")));
        orderStatusList.add(new OrderStatus(R.drawable.ic_notifications, new Status(4, "Delivered")));
        orderStatusList.add(new OrderStatus(R.drawable.ic_cancel, new Status(5, "Cancelled")));
        orderStatusList.add(new OrderStatus(R.drawable.ic_check_box, new Status(7, "Received")));
        orderStatusList.add(new OrderStatus(R.drawable.ic_playlist_remove, new Status(9, "Returned")));
    }

    public static List<OrderStatus> getOrderStatusList() {
        return orderStatusList;
    }

    private OrderStatus(int image, Status status) {
        this.image = image;
        this.status = status;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
