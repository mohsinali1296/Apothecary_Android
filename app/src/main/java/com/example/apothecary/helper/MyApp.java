package com.example.apothecary.helper;

import android.app.Application;

public class MyApp extends Application {

    private static MyApp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
    }

    public static synchronized MyApp getInstance(){
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener){
     ConnectivityReceiver.listener = listener;
    }


}
