package com.example.apothecary.models.brands;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Brands {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("Brand_Name")
    @Expose
    private String brandName;
    @SerializedName("SubCategoryId")
    @Expose
    private Integer subCategoryId;
    @SerializedName("Category_Name")
    @Expose
    private String categoryName;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;

    public Integer getId() {
        return id;
    }

    public String getBrandName() {
        return brandName;
    }

    public Integer getSubCategoryId() {
        return subCategoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getImage() {
        return image;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
