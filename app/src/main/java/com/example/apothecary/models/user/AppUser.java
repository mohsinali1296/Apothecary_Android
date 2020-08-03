package com.example.apothecary.models.user;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppUser implements Parcelable {

    @SerializedName("Id")
    @Expose
    private Integer userId;
    @SerializedName("fullname")
    @Expose
    private String fullname;
    @SerializedName("contact")
    @Expose
    private String contact;
    @SerializedName("local_Address")
    @Expose
    private String localAddress;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("Country")
    @Expose
    private String country;
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

    public AppUser(String email, String pass) {
        this.email = email;
        this.pass = pass;
    }

    public AppUser(String fullname , String contact , String email , String pass) {
        this.fullname = fullname;
        this.contact= contact;
        this.email= email;
        this.pass=pass;
    }

    public AppUser(String fullname ,  String email , String pass) {
        this.fullname = fullname;
        this.email= email;
        this.pass=pass;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getFullname() {
        return fullname;
    }

    public String getContact() {
        return contact;
    }

    public String getLocalAddress() {
        return localAddress;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getEmail() {
        return email;
    }

    public String getPass() {
        return null;
    }

    public String getImage() {
        return image;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public static Creator<AppUser> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.userId);
        dest.writeString(this.fullname);
        dest.writeString(this.contact);
        dest.writeString(this.localAddress);
        dest.writeString(this.city);
        dest.writeString(this.country);
        dest.writeString(this.email);
        dest.writeString(this.pass);
        dest.writeString(this.image);
        dest.writeString(this.imageUrl);
    }

    protected AppUser(Parcel in) {
        this.userId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.fullname = in.readString();
        this.contact = in.readString();
        this.localAddress = in.readString();
        this.city = in.readString();
        this.country = in.readString();
        this.email = in.readString();
        this.pass = in.readString();
        this.image = in.readString();
        this.imageUrl = in.readString();
    }

    public static final Parcelable.Creator<AppUser> CREATOR = new Parcelable.Creator<AppUser>() {
        @Override
        public AppUser createFromParcel(Parcel source) {
            return new AppUser(source);
        }

        @Override
        public AppUser[] newArray(int size) {
            return new AppUser[size];
        }
    };
}
