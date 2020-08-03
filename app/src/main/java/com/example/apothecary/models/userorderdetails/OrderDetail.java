
package com.example.apothecary.models.userorderdetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDetail {

    @SerializedName("Product_Id")
    @Expose
    private Integer productId;
    @SerializedName("Product_Name")
    @Expose
    private String productName;
    @SerializedName("Item_Description")
    @Expose
    private String itemDescription;
    @SerializedName("Pharmacy_Name")
    @Expose
    private String pharmacyName;
    @SerializedName("Pharm_Id")
    @Expose
    private Integer pharmId;
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
    @SerializedName("OrderDetail_Id")
    @Expose
    private Integer orderDetailId;
    @SerializedName("user_Id")
    @Expose
    private Integer userId;
    @SerializedName("userOrder_Id")
    @Expose
    private Integer userOrderId;
    @SerializedName("ItemOrdered_Quantity")
    @Expose
    private Integer itemOrderedQuantity;
    @SerializedName("total_Price")
    @Expose
    private Integer totalPrice;
    @SerializedName("Order_Status")
    @Expose
    private String orderStatus;
    @SerializedName("Stock_Type")
    @Expose
    private Integer stockType;
    @SerializedName("Item_Price")
    @Expose
    private Integer itemPrice;
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
    private Double deliveryCharges;
    @SerializedName("Total_Amount")
    @Expose
    private Integer totalAmount;
    @SerializedName("Total_Item")
    @Expose
    private Integer totalItem;
    @SerializedName("prescription_image")
    @Expose
    private String prescriptionImage;
    @SerializedName("prescription_image_url")
    @Expose
    private String prescriptionImageUrl;
    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("Time")
    @Expose
    private String time;

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

    public Integer getPharmId() {
        return pharmId;
    }

    public void setPharmId(Integer pharmId) {
        this.pharmId = pharmId;
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

    public Integer getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Integer orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserOrderId() {
        return userOrderId;
    }

    public void setUserOrderId(Integer userOrderId) {
        this.userOrderId = userOrderId;
    }

    public Integer getItemOrderedQuantity() {
        return itemOrderedQuantity;
    }

    public void setItemOrderedQuantity(Integer itemOrderedQuantity) {
        this.itemOrderedQuantity = itemOrderedQuantity;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getStockType() {
        return stockType;
    }

    public void setStockType(Integer stockType) {
        this.stockType = stockType;
    }

    public Integer getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Integer itemPrice) {
        this.itemPrice = itemPrice;
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

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getLeafPrice() {
        return leafPrice;
    }

    public void setLeafPrice(Integer leafPrice) {
        this.leafPrice = leafPrice;
    }

    public Integer getBoxPrice() {
        return boxPrice;
    }

    public void setBoxPrice(Integer boxPrice) {
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

    public void setDeliveryCharges(Double deliveryCharges) {
        this.deliveryCharges = deliveryCharges;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(Integer totalItem) {
        this.totalItem = totalItem;
    }

    public String getPrescriptionImage() {
        return prescriptionImage;
    }

    public void setPrescriptionImage(String prescriptionImage) {
        this.prescriptionImage = prescriptionImage;
    }

    public String getPrescriptionImageUrl() {
        return prescriptionImageUrl;
    }

    public void setPrescriptionImageUrl(String prescriptionImageUrl) {
        this.prescriptionImageUrl = prescriptionImageUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
