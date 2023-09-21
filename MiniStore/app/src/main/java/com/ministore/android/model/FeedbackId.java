package com.ministore.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeedbackId {

@SerializedName("userId")
@Expose
private int userId;
@SerializedName("productId")
@Expose
private int idProduct;

public int getUserId() {
return userId;
}

public void setUserId(int userId) {
this.userId = userId;
}

public int getIdProduct() {
return idProduct;
}

public void setIdProduct(int idProduct) {
this.idProduct = idProduct;
}

}