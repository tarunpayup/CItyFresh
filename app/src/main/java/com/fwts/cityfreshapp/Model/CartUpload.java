package com.fwts.cityfreshapp.Model;

public class CartUpload {
    String productName,productQty,totalPrice,productImage;

    public CartUpload() {
    }

    public CartUpload(String productName, String productQty, String totalPrice, String productImage) {
        this.productName = productName;
        this.productQty = productQty;
        this.totalPrice = totalPrice;
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductQty() {
        return productQty;
    }

    public void setProductQty(String productQty) {
        this.productQty = productQty;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
}
