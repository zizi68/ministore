package com.ministore.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StatusId implements Serializable {

    @SerializedName("id")
    @Expose
    private int statusId;
    @SerializedName("description")
    @Expose
    private String description;

    public StatusId(int statusId, String description) {
        this.statusId = statusId;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

}