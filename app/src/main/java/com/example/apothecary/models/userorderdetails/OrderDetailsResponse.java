package com.example.apothecary.models.userorderdetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDetailsResponse {
    @SerializedName("user_Id")
    @Expose
    private String userId;
    @SerializedName("userOrder_Id")
    @Expose
    private String userOrder_Id;
    @SerializedName("Pharm_Id")
    @Expose
    private String Pharm_Id;
    @SerializedName("stock_Id")
    @Expose
    private String stock_Id;
    @SerializedName("qty")
    @Expose
    private String qty;
    @SerializedName("total_Price")
    @Expose
    private String total_Price;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("stock_type")
    @Expose
    private String stock_type;
    @SerializedName("Id")
    @Expose
    private int Id;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserOrder_Id() {
        return userOrder_Id;
    }

    public void setUserOrder_Id(String userOrder_Id) {
        this.userOrder_Id = userOrder_Id;
    }

    public String getPharm_Id() {
        return Pharm_Id;
    }

    public void setPharm_Id(String pharm_Id) {
        Pharm_Id = pharm_Id;
    }

    public String getStock_Id() {
        return stock_Id;
    }

    public void setStock_Id(String stock_Id) {
        this.stock_Id = stock_Id;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getTotal_Price() {
        return total_Price;
    }

    public void setTotal_Price(String total_Price) {
        this.total_Price = total_Price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStock_type() {
        return stock_type;
    }

    public void setStock_type(String stock_type) {
        this.stock_type = stock_type;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
