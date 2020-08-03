package com.example.apothecary.models.favourite;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Favourite implements Parcelable {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("stockId")
    @Expose
    private Integer stockId;
    @SerializedName("type")
    @Expose
    private Integer type;

    public Favourite(Integer userId, Integer stockId, Integer type) {
        this.userId = userId;
        this.stockId = stockId;
        this.type = type;
    }

    public Favourite(Integer id, Integer userId, Integer stockId, Integer type) {
        this.id = id;
        this.userId = userId;
        this.stockId = stockId;
        this.type = type;
    }

    public Favourite() {
    }

    public Integer getId() {
        return id;
    }


    public Integer getUserId() {
        return userId;
    }


    public Integer getStockId() {
        return stockId;
    }


    public Integer getType() {
        return type;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.userId);
        dest.writeValue(this.stockId);
        dest.writeValue(this.type);
    }

    protected Favourite(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.userId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.stockId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.type = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<Favourite> CREATOR = new Parcelable.Creator<Favourite>() {
        @Override
        public Favourite createFromParcel(Parcel source) {
            return new Favourite(source);
        }

        @Override
        public Favourite[] newArray(int size) {
            return new Favourite[size];
        }
    };
}
