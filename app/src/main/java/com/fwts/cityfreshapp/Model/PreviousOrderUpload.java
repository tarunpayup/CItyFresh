package com.fwts.cityfreshapp.Model;

public class PreviousOrderUpload {
    String dateTime,ppName,ppQty,ppPrice;

    public PreviousOrderUpload() {
    }

    public PreviousOrderUpload(String dateTime, String ppName, String ppQty, String ppPrice) {
        this.dateTime = dateTime;
        this.ppName = ppName;
        this.ppQty = ppQty;
        this.ppPrice = ppPrice;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getPpName() {
        return ppName;
    }

    public void setPpName(String ppName) {
        this.ppName = ppName;
    }

    public String getPpQty() {
        return ppQty;
    }

    public void setPpQty(String ppQty) {
        this.ppQty = ppQty;
    }

    public String getPpPrice() {
        return ppPrice;
    }

    public void setPpPrice(String ppPrice) {
        this.ppPrice = ppPrice;
    }

}
