
package com.example.apothecary.models.pharmacy;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pharmacy {

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
}
