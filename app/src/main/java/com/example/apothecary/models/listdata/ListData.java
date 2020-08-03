
package com.example.apothecary.models.listdata;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ListData implements Serializable, Parcelable {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("DataName")
    @Expose
    private String dataName;
    @SerializedName("ListName")
    @Expose
    private String listName;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("description")
    @Expose
    private String description;

    public Integer getId() {
        return id;
    }

    public String getDataName() {
        return dataName;
    }

    public String getListName() {
        return listName;
    }

    public String getImage() {
        return image;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public static Creator<ListData> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.dataName);
        dest.writeString(this.listName);
        dest.writeString(this.image);
        dest.writeString(this.imageUrl);
        dest.writeString(this.description);
    }

    public ListData() {
    }

    protected ListData(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.dataName = in.readString();
        this.listName = in.readString();
        this.image = in.readString();
        this.imageUrl = in.readString();
        this.description = in.readString();
    }

    public static final Parcelable.Creator<ListData> CREATOR = new Parcelable.Creator<ListData>() {
        @Override
        public ListData createFromParcel(Parcel source) {
            return new ListData(source);
        }

        @Override
        public ListData[] newArray(int size) {
            return new ListData[size];
        }
    };
}
