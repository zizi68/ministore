package com.ministore.android.model;

public class AccountOption {

    public interface OnOptionListener {
        void onClick();
    }

    private int image;
    private String name;
    private OnOptionListener onOptionListener;

    public AccountOption(int image, String name, OnOptionListener onOptionListener) {
        this.image = image;
        this.name = name;
        this.onOptionListener = onOptionListener;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OnOptionListener getOnOptionListener() {
        return onOptionListener;
    }

    public void setOnOptionListener(OnOptionListener onOptionListener) {
        this.onOptionListener = onOptionListener;
    }
}
