package com.fwts.cityfreshapp.Model;

public class AdminCartUpload {
    String date, name, phone, address,productName,productQty,totalPrice;

    public AdminCartUpload() {
    }

    public AdminCartUpload(String date, String name, String phone, String address, String productName, String productQty, String totalPrice) {
        this.date = date;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.productName = productName;
        this.productQty = productQty;
        this.totalPrice = totalPrice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
}
