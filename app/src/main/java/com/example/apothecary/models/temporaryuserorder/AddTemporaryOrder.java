package com.example.apothecary.models.temporaryuserorder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddTemporaryOrder {
    @SerializedName("Stock_Id")
    @Expose
    private int stockId;
    @SerializedName("qty")
    @Expose
    private int qty;
    @SerializedName("user_Id")
    @Expose
    private int userId;
    @SerializedName("Pharm_Id")
    @Expose
    private int pharmId;
    @SerializedName("total_amount")
    @Expose
    private double total_amount;
    @SerializedName("total_price")
    @Expose
    private double total_price;
    @SerializedName("price")
    @Expose
    private double price;
    @SerializedName("stock_type")
    @Expose
    private int stock_type;
    @SerializedName("prescription_image")
    @Expose
    private String prescriptionImage;

    public AddTemporaryOrder(int stockId, int qty, int userId, int pharmId, double total_amount,String prescriptionImage) {
        this.stockId = stockId;
        this.qty = qty;
        this.userId = userId;
        this.pharmId = pharmId;
        this.total_amount=total_amount;
        this.prescriptionImage = prescriptionImage;
    }

    public AddTemporaryOrder(int stockId, int qty, int userId, int pharmId, double total_amount, double total_price, double price, int stock_type, String prescriptionImage) {
        this.stockId = stockId;
        this.qty = qty;
        this.userId = userId;
        this.pharmId = pharmId;
        this.total_amount = total_amount;
        this.total_price = total_price;
        this.price = price;
        this.stock_type = stock_type;
        this.prescriptionImage = prescriptionImage;
    }
}
