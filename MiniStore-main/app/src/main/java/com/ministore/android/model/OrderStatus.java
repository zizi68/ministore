package com.ministore.android.model;

import com.ministore.android.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrderStatus implements Serializable {
    private int image;
    private StatusId statusId;

    private static List<OrderStatus> orderStatusList;

    static {
        orderStatusList = new ArrayList<>();
        orderStatusList.add(new OrderStatus(R.drawable.ic_playlist_add, new StatusId(1, "Processing")));
        orderStatusList.add(new OrderStatus(R.drawable.ic_playlist_remove, new StatusId(2, "Requesting cancellation")));
        orderStatusList.add(new OrderStatus(R.drawable.ic_delivery_dining, new StatusId(3, "Delivering")));
        orderStatusList.add(new OrderStatus(R.drawable.ic_check_box, new StatusId(4, "Delivered")));
        orderStatusList.add(new OrderStatus(R.drawable.ic_cancel, new StatusId(5, "Cancelled")));
    }

    public static List<OrderStatus> getOrderStatusList() {
        return orderStatusList;
    }

    private OrderStatus(int image, StatusId statusId) {
        this.image = image;
        this.statusId = statusId;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public StatusId getStatusId() {
        return statusId;
    }

    public void setStatusId(StatusId statusId) {
        this.statusId = statusId;
    }
}
