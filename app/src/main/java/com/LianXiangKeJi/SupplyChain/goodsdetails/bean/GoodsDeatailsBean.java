package com.LianXiangKeJi.SupplyChain.goodsdetails.bean;

import android.os.Parcelable;

import java.io.Serializable;

public class GoodsDeatailsBean implements Serializable {
    private String image;
    private String name;
    private String price;
    private String old_price;
    private String shipping;
    private String monthsell;
    private String stock;
    private String from;
    private String spec;

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOld_price() {
        return old_price;
    }

    public void setOld_price(String old_price) {
        this.old_price = old_price;
    }

    public String getShipping() {
        return shipping;
    }

    public void setShipping(String shipping) {
        this.shipping = shipping;
    }

    public String getMonthsell() {
        return monthsell;
    }

    public void setMonthsell(String monthsell) {
        this.monthsell = monthsell;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
