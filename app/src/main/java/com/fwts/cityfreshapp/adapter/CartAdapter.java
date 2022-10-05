package com.fwts.cityfreshapp.adapter;

import java.io.Serializable;

public class CartAdapter implements Serializable {
    String key,productName,productQty,totalPrice,productImage;

    public void setKey(String key) {
        this.key = key;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductQty(String productQty) {
        this.productQty = productQty;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getKey() {
        return key;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductQty() {
        return productQty;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public String getProductImage() {
        return productImage;
    }
}

