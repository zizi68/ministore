package com.ministore.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImportDetail {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("imports")
    @Expose
    private Import imports;

    @SerializedName("price")
    @Expose
    private float price;

    @SerializedName("product")
    @Expose
    private Product product;

    @SerializedName("quantity")
    @Expose
    private int quantity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Import getImports() {
        return imports;
    }

    public void setImports(Import imports) {
        this.imports = imports;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}