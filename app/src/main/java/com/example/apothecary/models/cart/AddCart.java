
package com.example.apothecary.models.cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddCart {

    @SerializedName("user_Id")
    @Expose
    private Integer userId;
    @SerializedName("Stock_Id")
    @Expose
    private Integer stockId;
    @SerializedName("qty")
    @Expose
    private Integer qty;
    @SerializedName("totalPrice")
    @Expose
    private Double totalPrice;
    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("type")
    @Expose
    private Integer type;

    public AddCart(Integer qty, Double totalPrice) {
        this.qty = qty;
        this.totalPrice = totalPrice;
    }

    public AddCart(Integer userId, Integer stockId, Integer qty, Double totalPrice,int type) {
        this.userId = userId;
        this.stockId = stockId;
        this.qty = qty;
        this.totalPrice = totalPrice;
        this.type=type;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getStockId() {
        return stockId;
    }

    public Integer getQty() {
        return qty;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public Integer getId() {
        return id;
    }
}
