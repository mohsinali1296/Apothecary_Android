package com.example.apothecary.models.favourite;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {
    @SerializedName("Product_Id")
    @Expose
    private Integer productId;
    @SerializedName("Product_Name")
    @Expose
    private String productName;
    @SerializedName("Item_Description")
    @Expose
    private String itemDescription;
    @SerializedName("Item_Detailed_Description")
    @Expose
    private String itemDetailedDescription;
    @SerializedName("Formula_Id")
    @Expose
    private Integer formulaId;
    @SerializedName("Formula_Name")
    @Expose
    private String formulaName;
    @SerializedName("Pharmacy_Id")
    @Expose
    private Integer pharmacyId;
    @SerializedName("Pharmacy_Name")
    @Expose
    private String pharmacyName;
    @SerializedName("Pharmacy_Contact")
    @Expose
    private String pharmacyContact;
    @SerializedName("Pharmacy_Address")
    @Expose
    private String pharmacyAddress;
    @SerializedName("Pharmacy_Email")
    @Expose
    private String pharmacyEmail;
    @SerializedName("Latitude")
    @Expose
    private Double latitude;
    @SerializedName("Longitude")
    @Expose
    private Double longitude;
    @SerializedName("Category_Id")
    @Expose
    private Integer categoryId;
    @SerializedName("Category_Name")
    @Expose
    private String categoryName;
    @SerializedName("SubCategory_Id")
    @Expose
    private Integer subCategoryId;
    @SerializedName("SubCategory_Name")
    @Expose
    private String subCategoryName;
    @SerializedName("Brand_Id")
    @Expose
    private Integer brandId;
    @SerializedName("Brand_Name")
    @Expose
    private String brandName;
    @SerializedName("unit_Qty")
    @Expose
    private Integer unitQty;
    @SerializedName("qty_per_leaf")
    @Expose
    private Integer qtyPerLeaf;
    @SerializedName("qty_per_box")
    @Expose
    private Integer qtyPerBox;
    @SerializedName("unit_price")
    @Expose
    private Integer unitPrice;
    @SerializedName("leaf_price")
    @Expose
    private Integer leafPrice;
    @SerializedName("box_price")
    @Expose
    private Integer boxPrice;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("Available")
    @Expose
    private Integer available;
    @SerializedName("Delivery_Charges")
    @Expose
    private Object deliveryCharges;
    @SerializedName("Fav_Id")
    @Expose
    private Integer favId;

    public Integer getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public String getItemDetailedDescription() {
        return itemDetailedDescription;
    }

    public Integer getFormulaId() {
        return formulaId;
    }

    public String getFormulaName() {
        return formulaName;
    }

    public Integer getPharmacyId() {
        return pharmacyId;
    }

    public String getPharmacyName() {
        return pharmacyName;
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

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public Integer getSubCategoryId() {
        return subCategoryId;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public Integer getUnitQty() {
        return unitQty;
    }

    public Integer getQtyPerLeaf() {
        return qtyPerLeaf;
    }

    public Integer getQtyPerBox() {
        return qtyPerBox;
    }

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public Integer getLeafPrice() {
        return leafPrice;
    }

    public Integer getBoxPrice() {
        return boxPrice;
    }

    public String getImage() {
        return image;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Integer getAvailable() {
        return available;
    }

    public Object getDeliveryCharges() {
        return deliveryCharges;
    }

    public Integer getFavId() {
        return favId;
    }
}
