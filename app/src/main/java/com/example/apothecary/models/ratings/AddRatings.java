package com.example.apothecary.models.ratings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddRatings {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("Pharm_Id")
    @Expose
    private Integer pharmId;
    @SerializedName("User_Id")
    @Expose
    private Integer userId;
    @SerializedName("rating")
    @Expose
    private Integer rating;

    public AddRatings(Integer pharmId, Integer userId, Integer rating) {
        this.pharmId = pharmId;
        this.userId = userId;
        this.rating = rating;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPharmId() {
        return pharmId;
    }

    public void setPharmId(Integer pharmId) {
        this.pharmId = pharmId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
