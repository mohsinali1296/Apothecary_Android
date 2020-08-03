package com.example.apothecary.models.pharmacy;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Pharmacies implements Serializable {
    @SerializedName("Pharmacy_Id")
    @Expose
    private Integer pharmacyId;
    @SerializedName("Pharmacy_Name")
    @Expose
    private String pharmacyName;
    @SerializedName("Pharmacy_Distance")
    @Expose
    private Double pharmacyDistance;
    @SerializedName("Pharmacy_Latitude")
    @Expose
    private Double pharmacyLatitude;
    @SerializedName("Pharmacy_Longitude")
    @Expose
    private Double pharmacyLongitude;
    @SerializedName("Pharmacy_Contact")
    @Expose
    private String pharmacyContact;
    @SerializedName("Pharmacy_Address")
    @Expose
    private String pharmacyAddress;
    @SerializedName("Pharmacy_Email")
    @Expose
    private String pharmacyEmail;


    public Integer getPharmacyId() {
        return pharmacyId;
    }

    public String getPharmacyName() {
        return pharmacyName;
    }

    public Double getPharmacyDistance() {
        return pharmacyDistance;
    }

    public Double getPharmacyLatitude() {
        return pharmacyLatitude;
    }

    public Double getPharmacyLongitude() {
        return pharmacyLongitude;
    }

    public String getPharmacyContact() {
        return pharmacyContact;
    }

    public String getPharmacyAddress() {
        return pharmacyAddress;
    }

    public String getPharmacyEmail() {
        return pharmacyEmail;
    }


}
