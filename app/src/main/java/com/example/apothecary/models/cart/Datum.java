
package com.example.apothecary.models.cart;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum implements Parcelable {

    @SerializedName("Product_Id")
    @Expose
    private Integer productId;
    @SerializedName("Product_Name")
    @Expose
    private String productName;
    @SerializedName("Item_Description")
    @Expose
    private String itemDescription;
    @SerializedName("Pharm_Id")
    @Expose
    private Integer pharmacyId;
    @SerializedName("Pharmacy_Name")
    @Expose
    private String pharmacyName;
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
    @SerializedName("CartId")
    @Expose
    private Integer cartId;
    @SerializedName("user_Id")
    @Expose
    private Integer userId;
    @SerializedName("Quantity")
    @Expose
    private Integer quantity;
    @SerializedName("totalPrice")
    @Expose
    private Double totalPrice;
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
    private Double unitPrice;
    @SerializedName("leaf_price")
    @Expose
    private Double leafPrice;
    @SerializedName("box_price")
    @Expose
    private Double boxPrice;
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
    private Double deliveryCharges;
    @SerializedName("Cart_Type")
    @Expose
    private Integer cartType;
    @SerializedName("prescription_required")
    @Expose
    private Integer prescription_required;
    @SerializedName("PrecriptionRequired_Text")
    @Expose
    private String PrecriptionRequired_Text;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getPharmacyName() {
        return pharmacyName;
    }

    public void setPharmacyName(String pharmacyName) {
        this.pharmacyName = pharmacyName;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(Integer subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getUnitQty() {
        return unitQty;
    }

    public void setUnitQty(Integer unitQty) {
        this.unitQty = unitQty;
    }

    public Integer getQtyPerLeaf() {
        return qtyPerLeaf;
    }

    public void setQtyPerLeaf(Integer qtyPerLeaf) {
        this.qtyPerLeaf = qtyPerLeaf;
    }

    public Integer getQtyPerBox() {
        return qtyPerBox;
    }

    public void setQtyPerBox(Integer qtyPerBox) {
        this.qtyPerBox = qtyPerBox;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getLeafPrice() {
        return leafPrice;
    }

    public void setLeafPrice(Double leafPrice) {
        this.leafPrice = leafPrice;
    }

    public Double getBoxPrice() {
        return boxPrice;
    }

    public void setBoxPrice(Double boxPrice) {
        this.boxPrice = boxPrice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    public Double getDeliveryCharges() {
        return deliveryCharges;
    }

    public void setDeliveryCharges(Double deliveryCharges) { this.deliveryCharges = deliveryCharges; }

    public Integer getCartType() { return cartType; }

    public void setCartType(Integer cartType) { this.cartType = cartType; }

    public Integer getPharmacyId() {
        return pharmacyId;
    }

    public void setPharmacyId(Integer pharmacyId) {
        this.pharmacyId = pharmacyId;
    }

    public Integer getPrescription_required() {
        return prescription_required;
    }

    public void setPrescription_required(Integer prescription_required) {
        this.prescription_required = prescription_required;
    }

    public String getPrecriptionRequired_Text() {
        return PrecriptionRequired_Text;
    }

    public void setPrecriptionRequired_Text(String precriptionRequired_Text) {
        PrecriptionRequired_Text = precriptionRequired_Text;
    }

    public Datum() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.productId);
        dest.writeString(this.productName);
        dest.writeString(this.itemDescription);
        dest.writeValue(this.pharmacyId);
        dest.writeString(this.pharmacyName);
        dest.writeValue(this.categoryId);
        dest.writeString(this.categoryName);
        dest.writeValue(this.subCategoryId);
        dest.writeString(this.subCategoryName);
        dest.writeValue(this.cartId);
        dest.writeValue(this.userId);
        dest.writeValue(this.quantity);
        dest.writeValue(this.totalPrice);
        dest.writeValue(this.unitQty);
        dest.writeValue(this.qtyPerLeaf);
        dest.writeValue(this.qtyPerBox);
        dest.writeValue(this.unitPrice);
        dest.writeValue(this.leafPrice);
        dest.writeValue(this.boxPrice);
        dest.writeString(this.image);
        dest.writeString(this.imageUrl);
        dest.writeValue(this.available);
        dest.writeValue(this.deliveryCharges);
        dest.writeValue(this.cartType);
        dest.writeValue(this.prescription_required);
        dest.writeString(this.PrecriptionRequired_Text);
    }

    protected Datum(Parcel in) {
        this.productId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.productName = in.readString();
        this.itemDescription = in.readString();
        this.pharmacyId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.pharmacyName = in.readString();
        this.categoryId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.categoryName = in.readString();
        this.subCategoryId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.subCategoryName = in.readString();
        this.cartId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.userId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.quantity = (Integer) in.readValue(Integer.class.getClassLoader());
        this.totalPrice = (Double) in.readValue(Double.class.getClassLoader());
        this.unitQty = (Integer) in.readValue(Integer.class.getClassLoader());
        this.qtyPerLeaf = (Integer) in.readValue(Integer.class.getClassLoader());
        this.qtyPerBox = (Integer) in.readValue(Integer.class.getClassLoader());
        this.unitPrice = (Double) in.readValue(Double.class.getClassLoader());
        this.leafPrice = (Double) in.readValue(Double.class.getClassLoader());
        this.boxPrice = (Double) in.readValue(Double.class.getClassLoader());
        this.image = in.readString();
        this.imageUrl = in.readString();
        this.available = (Integer) in.readValue(Integer.class.getClassLoader());
        this.deliveryCharges = (Double) in.readValue(Double.class.getClassLoader());
        this.cartType = (Integer) in.readValue(Integer.class.getClassLoader());
        this.prescription_required = (Integer) in.readValue(Integer.class.getClassLoader());
        this.PrecriptionRequired_Text = in.readString();
    }

    public static final Parcelable.Creator<Datum> CREATOR = new Parcelable.Creator<Datum>() {
        @Override
        public Datum createFromParcel(Parcel source) {
            return new Datum(source);
        }

        @Override
        public Datum[] newArray(int size) {
            return new Datum[size];
        }
    };
}
