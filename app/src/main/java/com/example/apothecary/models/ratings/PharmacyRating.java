
package com.example.apothecary.models.ratings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PharmacyRating {

    @SerializedName("Pharm_Id")
    @Expose
    private Integer pharmId;
    @SerializedName("ratingavg")
    @Expose
    private String ratingavg;
    @SerializedName("ratingsum")
    @Expose
    private Integer ratingsum;
    @SerializedName("rating5")
    @Expose
    private Integer rating5;
    @SerializedName("rating4")
    @Expose
    private Integer rating4;
    @SerializedName("rating3")
    @Expose
    private Integer rating3;
    @SerializedName("rating2")
    @Expose
    private Integer rating2;
    @SerializedName("rating1")
    @Expose
    private Integer rating1;

    public Integer getPharmId() {
        return pharmId;
    }

    public void setPharmId(Integer pharmId) {
        this.pharmId = pharmId;
    }

    public String getRatingavg() {
        return ratingavg;
    }

    public void setRatingavg(String ratingavg) {
        this.ratingavg = ratingavg;
    }

    public Integer getRatingsum() {
        return ratingsum;
    }

    public void setRatingsum(Integer ratingsum) {
        this.ratingsum = ratingsum;
    }

    public Integer getRating5() {
        return rating5;
    }

    public void setRating5(Integer rating5) {
        this.rating5 = rating5;
    }

    public Integer getRating4() {
        return rating4;
    }

    public void setRating4(Integer rating4) {
        this.rating4 = rating4;
    }

    public Integer getRating3() {
        return rating3;
    }

    public void setRating3(Integer rating3) {
        this.rating3 = rating3;
    }

    public Integer getRating2() {
        return rating2;
    }

    public void setRating2(Integer rating2) {
        this.rating2 = rating2;
    }

    public Integer getRating1() {
        return rating1;
    }

    public void setRating1(Integer rating1) {
        this.rating1 = rating1;
    }

}
