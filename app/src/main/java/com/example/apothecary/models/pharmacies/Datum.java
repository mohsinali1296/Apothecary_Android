
package com.example.apothecary.models.pharmacies;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Datum implements Serializable {
    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("Pharm_Name")
    @Expose
    private String pharmName;
    @SerializedName("Contact")
    @Expose
    private String contact;
    @SerializedName("Pharmacy_Address")
    @Expose
    private String pharmacyAddress;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("Latitude")
    @Expose
    private Double latitude;
    @SerializedName("Longitude")
    @Expose
    private Double longitude;
    @SerializedName("Pharmacy_Distance")
    @Expose
    private Double pharmacyDistance;
    @SerializedName("Average_Rating")
    @Expose
    private String Average_Rating;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;



    public String getAverage_Rating() { return Average_Rating; }

    public Integer getId() {
        return id;
    }

    public String getPharmName() {
        return pharmName;
    }

    public String getContact() {
        return contact;
    }

    public String getPharmacyAddress() {
        return pharmacyAddress;
    }

    public String getEmail() {
        return email;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getPharmacyDistance() {
        return pharmacyDistance;
    }

    public String getImage() {
        return image;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Datum(String pharmName ,Double latitude, Double longitude) {
        this.pharmName=pharmName;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
