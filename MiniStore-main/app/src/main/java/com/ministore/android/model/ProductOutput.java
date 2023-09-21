package com.ministore.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductOutput {

    @SerializedName("page")
    @Expose
    private int page;
    @SerializedName("totalPage")
    @Expose
    private int totalPage;
    @SerializedName("listResult")
    @Expose
    private List<Product> listResult = null;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<Product> getListResult() {
        return listResult;
    }

    public void setListResult(List<Product> listResult) {
        this.listResult = listResult;
    }

}