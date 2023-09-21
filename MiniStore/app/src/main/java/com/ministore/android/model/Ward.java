package com.ministore.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Ward implements Serializable {

    @SerializedName("id")
    @Expose
    private int wardId;
    @SerializedName("name")
    @Expose
    private String wardName;
    @SerializedName("prefix")
    @Expose
    private String wardPrefix;
    @SerializedName("district")
    @Expose
    private District district;

    public int getWardId() {
        return wardId;
    }

    public void setWardId(int wardId) {
        this.wardId = wardId;
    }

    public String getWardName() {
        return wardName;
    }

    public void setWardName(String wardName) {
        this.wardName = wardName;
    }

    public String getWardPrefix() {
        return wardPrefix;
    }

    public void setWardPrefix(String wardPrefix) {
        this.wardPrefix = wardPrefix;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

}