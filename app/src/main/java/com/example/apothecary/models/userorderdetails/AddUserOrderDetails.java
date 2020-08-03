package com.example.apothecary.models.userorderdetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddUserOrderDetails {
    @SerializedName("user_Id")
    @Expose
    private int userId;
    @SerializedName("userOrder_Id")
    @Expose
    private int userOrder_Id;
    @SerializedName("Pharm_Id")
    @Expose
    private int Pharm_Id;
    @SerializedName("stock_Id")
    @Expose
    private int stock_Id;
    @SerializedName("qty")
    @Expose
    private int qty;
    @SerializedName("total_Price")
    @Expose
    private double total_Price;
    @SerializedName("price")
    @Expose
    private double price;
    @SerializedName("stock_type")
    @Expose
    private int stock_type;

    public AddUserOrderDetails(int userId, int userOrder_Id, int pharm_Id, int stock_Id, int qty, double total_Price, double price, int stock_type) {
        this.userId = userId;
        this.userOrder_Id = userOrder_Id;
        Pharm_Id = pharm_Id;
        this.stock_Id = stock_Id;
        this.qty = qty;
        this.total_Price = total_Price;
        this.price = price;
        this.stock_type = stock_type;
    }
}
