package com.example.apothecary;

import android.app.AlertDialog;
import android.content.Context;

import com.example.apothecary.api.ApothecaryClient;

import retrofit2.Retrofit;

public class Utils {
    public static com.example.apothecary.api.BloggerApi getBloggerApi() {
        return com.example.apothecary.api.BloggerClient.getFoodClient().create(com.example.apothecary.api.BloggerApi.class);
    }

    public static com.example.apothecary.api.ApothecaryApi getApothecaryApi() {
        return com.example.apothecary.api.ApothecaryClient.geApothecaryClient().create(com.example.apothecary.api.ApothecaryApi.class);
    }

    public static Retrofit getRetrofitAttribute(){
        return ApothecaryClient.retrofit;
    }

    public static AlertDialog showDialogMessage(Context context, String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).setTitle(title).setMessage(message).show();
        if (alertDialog.isShowing()) {
            alertDialog.cancel();
        }
        return alertDialog;
    }
}
