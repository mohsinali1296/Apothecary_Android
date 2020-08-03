
package com.example.apothecary.models.user;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("contact")
    @Expose
    private List<String> contact = null;
    @SerializedName("email")
    @Expose
    private List<String> email = null;

    public List<String> getContact() {
        return contact;
    }

    public List<String> getEmail() {
        return email;
    }

}
