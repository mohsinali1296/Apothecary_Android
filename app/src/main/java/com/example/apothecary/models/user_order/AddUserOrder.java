package com.example.apothecary.models.user_order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddUserOrder {
    @SerializedName("user_Id")
    @Expose
    private int userId;
    @SerializedName("item_count")
    @Expose
    private int itemCount;
    @SerializedName("total_amount")
    @Expose
    private double totalAmount;
    @SerializedName("prescription_image")
    @Expose
    private String prescription_image;

    public AddUserOrder(int userId, int itemCount, double totalAmount, String prescription_image) {
        this.userId = userId;
        this.itemCount = itemCount;
        this.totalAmount = totalAmount;
        this.prescription_image = prescription_image;
    }
}
