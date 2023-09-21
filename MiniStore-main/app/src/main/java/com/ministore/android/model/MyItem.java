package com.ministore.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyItem {

    @SerializedName("label")
    @Expose
    private String label;

    @SerializedName("value")
    @Expose
    private int value;

    public MyItem() {
    }

    public MyItem(String label, int value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
