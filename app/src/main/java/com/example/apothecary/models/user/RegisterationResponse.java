
package com.example.apothecary.models.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterationResponse {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private Message message;
    @SerializedName("body")
    @Expose
    private Body body;

    public Boolean getSuccess() {
        return success;
    }

    public Message getMessage() {
        return message;
    }

    public Body getBody() {
        return body;
    }

}
