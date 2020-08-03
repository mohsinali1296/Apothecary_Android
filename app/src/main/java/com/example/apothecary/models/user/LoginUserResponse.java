
package com.example.apothecary.models.user;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginUserResponse {

    @SerializedName("error")
    @Expose
    private boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("body")
    @Expose
    private List<User> body = null;

    public boolean getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public List<User> getBody() {
        return body;
    }


}
