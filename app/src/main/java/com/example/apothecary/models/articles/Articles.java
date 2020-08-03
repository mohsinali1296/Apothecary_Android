
package com.example.apothecary.models.articles;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.example.apothecary.models.articles.*;

public class Articles {

    @SerializedName("nextPageToken")
    @Expose
    private String nextPageToken;
    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("items")
    @Expose
    private List<com.example.apothecary.models.articles.Item> items = null;
    @SerializedName("etag")
    @Expose
    private String etag;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public List<com.example.apothecary.models.articles.Item> getItems() {
        return items;
    }

    public void setItems(List<com.example.apothecary.models.articles.Item> items) {
        this.items = items;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

}
