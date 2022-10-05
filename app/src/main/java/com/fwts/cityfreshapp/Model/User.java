package com.fwts.cityfreshapp.Model;

public class User {
    private String name, phone, pwd, area,address,landmark;
    public User(){

    }

    public User(String name, String phone, String pwd, String area, String address) {
        this.name = name;
        this.phone = phone;
        this.pwd = pwd;
        this.area = area;
        this.address = address;
        this.landmark = landmark;
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

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }
}




