
package com.example.apothecary.models.user_order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddUserOrderResponse {

    @SerializedName("user_Id")
    @Expose
    private String userId;
    @SerializedName("item_count")
    @Expose
    private String itemCount;
    @SerializedName("total_amount")
    @Expose
    private String totalAmount;
    @SerializedName("prescription_image")
    @Expose
    private String prescription_image;
    @SerializedName("prescription_image_url")
    @Expose
    private String prescription_image_url;
    @SerializedName("Id")
    @Expose
    private Integer id;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getItemCount() {
        return itemCount;
    }

    public void setItemCount(String itemCount) {
        this.itemCount = itemCount;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrescription_image() {
        return prescription_image;
    }

    public void setPrescription_image(String prescription_image) {
        this.prescription_image = prescription_image;
    }

    public String getPrescription_image_url() {
        return prescription_image_url;
    }

    public void setPrescription_image_url(String prescription_image_url) {
        this.prescription_image_url = prescription_image_url;
    }
}
