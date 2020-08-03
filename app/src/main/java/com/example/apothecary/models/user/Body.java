
package com.example.apothecary.models.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Body {

    @SerializedName("fullname")
    @Expose
    private String fullname;
    @SerializedName("contact")
    @Expose
    private String contact;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("pass")
    @Expose
    private String pass;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("Id")
    @Expose
    private Integer id;

    public String getFullname() {
        return fullname;
    }

    public String getContact() {
        return contact;
    }
    public String getEmail() {
        return email;
    }
    public String getPass() { return null; }
    public Integer getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
