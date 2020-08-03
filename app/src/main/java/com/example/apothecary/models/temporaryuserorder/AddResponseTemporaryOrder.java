
package com.example.apothecary.models.temporaryuserorder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddResponseTemporaryOrder {

    @SerializedName("Stock_Id")
    @Expose
    private String stockId;
    @SerializedName("qty")
    @Expose
    private String qty;
    @SerializedName("user_Id")
    @Expose
    private String userId;
    @SerializedName("Pharm_Id")
    @Expose
    private String pharmId;
    @SerializedName("total_amount")
    @Expose
    private String total_amount;
    @SerializedName("total_price")
    @Expose
    private String total_price;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("stock_type")
    @Expose
    private String stock_type;
    @SerializedName("prescription_image")
    @Expose
    private String prescriptionImage;
    @SerializedName("prescription_image_url")
    @Expose
    private String prescription_image_url;

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
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

    @SerializedName("Id")
    @Expose
    private Integer id;

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPharmId() {
        return pharmId;
    }

    public void setPharmId(String pharmId) {
        this.pharmId = pharmId;
    }

    public String getPrescriptionImage() {
        return prescriptionImage;
    }

    public void setPrescriptionImage(String prescriptionImage) {
        this.prescriptionImage = prescriptionImage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrescription_image_url() { return prescription_image_url; }
}
