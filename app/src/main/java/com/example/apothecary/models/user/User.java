
package com.example.apothecary.models.user;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.annotation.Nullable;

public class User implements Parcelable {

    @SerializedName("Customer_Id")
    @Expose
    private Integer customerId;
    @SerializedName("Fullname")
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
    @SerializedName("image")
    @Expose
    @Nullable
    private String image;
    @SerializedName("imageUrl")
    @Expose
    @Nullable
    private String imageUrl;

    public User(String localAddress, String city, String country) {
        this.localAddress = localAddress;
        this.city = city;
        this.country = country;
    }

    public User(Integer customerId, String fullname, String contact, String localAddress, String city, String country, String email, String image, String imageUrl) {
        this.customerId = customerId;
        this.fullname = fullname;
        this.contact = contact;
        this.localAddress = localAddress;
        this.city = city;
        this.country = country;
        this.email = email;
        this.image = image;
        this.imageUrl = imageUrl;
    }

    public User(Integer customerId, String fullname, String contact, String localAddress, String city, String country, String email, @Nullable String image) {
        this.customerId = customerId;
        this.fullname = fullname;
        this.contact = contact;
        this.localAddress = localAddress;
        this.city = city;
        this.country = country;
        this.email = email;
        this.image = image;
    }

    public Integer getCustomerId() {
        return customerId;
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

    public void setImage(@Nullable String image) {
        this.image = image;
    }

    @Nullable
    public String getImage() {
        return image;
    }

    @Nullable
    public String getImageUrl() {
        return imageUrl;
    }

    public void setLocalAddress(String localAddress) {
        this.localAddress = localAddress;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.customerId);
        dest.writeString(this.fullname);
        dest.writeString(this.contact);
        dest.writeString(this.localAddress);
        dest.writeString(this.city);
        dest.writeString(this.country);
        dest.writeString(this.email);
        dest.writeString(this.image);
        dest.writeString(this.imageUrl);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.customerId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.fullname = in.readString();
        this.contact = in.readString();
        this.localAddress = in.readString();
        this.city = in.readString();
        this.country = in.readString();
        this.email = in.readString();
        this.image = in.readString();
        this.imageUrl = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
