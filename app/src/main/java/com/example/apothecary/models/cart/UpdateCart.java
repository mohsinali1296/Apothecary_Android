
package com.example.apothecary.models.cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateCart {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("Stock_Id")
    @Expose
    private Integer stockId;
    @SerializedName("qty")
    @Expose
    private Integer qty;
    @SerializedName("user_Id")
    @Expose
    private Integer userId;
    @SerializedName("deleted")
    @Expose
    private Integer deleted;
    @SerializedName("totalPrice")
    @Expose
    private Double totalPrice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStockId() {
        return stockId;
    }

    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

}
