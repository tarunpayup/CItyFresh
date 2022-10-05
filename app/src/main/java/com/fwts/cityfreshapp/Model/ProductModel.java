package com.fwts.cityfreshapp.Model;


import android.os.Parcel;
import android.os.Parcelable;

public class ProductModel implements Parcelable {
    public static final Creator<ProductModel> CREATOR = new Creator<ProductModel>() {
        @Override
        public ProductModel createFromParcel(Parcel in) {
            return new ProductModel(in);
        }

        @Override
        public ProductModel[] newArray(int size) {
            return new ProductModel[size];
        }
    };
    String id;
    String key;
    String image;
    String p_name;
    String p_price;
    String p_qty;
    String p_unit;
    String desc;
    String categoryType;

    public ProductModel() {

    }

    public ProductModel(String id, String key, String image, String p_name, String p_price,
                        String p_qty, String p_unit, String desc, String categoryType) {
        this.id = id;
        this.key = key;
        this.image = image;
        this.p_name = p_name;
        this.p_price = p_price;
        this.p_qty = p_qty;
        this.p_unit = p_unit;
        this.desc = desc;
        this.categoryType = categoryType;
    }

    protected ProductModel(Parcel in) {
        id = in.readString();
        key = in.readString();
        image = in.readString();
        p_name = in.readString();
        p_price = in.readString();
        p_qty = in.readString();
        p_unit = in.readString();
        desc = in.readString();
        categoryType = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(key);
        dest.writeString(image);
        dest.writeString(p_name);
        dest.writeString(p_price);
        dest.writeString(p_qty);
        dest.writeString(p_unit);
        dest.writeString(desc);
        dest.writeString(categoryType);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getP_price() {
        return p_price;
    }

    public void setP_price(String p_price) {
        this.p_price = p_price;
    }

    public String getP_qty() {
        return p_qty;
    }

    public void setP_qty(String p_qty) {
        this.p_qty = p_qty;
    }

    public String getP_unit() {
        return p_unit;
    }

    public void setP_unit(String p_unit) {
        this.p_unit = p_unit;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }
}
