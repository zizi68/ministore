package com.ministore.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReturnSummaryDTO {

    @SerializedName("returnDetail")
    @Expose
    private ReturnDetail returnDetail;
    @SerializedName("size")
    @Expose
    private int size;

    public ReturnDetail getReturnDetail() {
        return returnDetail;
    }

    public void setReturnDetail(ReturnDetail orderDetail) {
        this.returnDetail = returnDetail;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

}