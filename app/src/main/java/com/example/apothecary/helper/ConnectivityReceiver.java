package com.example.apothecary.helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectivityReceiver extends BroadcastReceiver {

    public static ConnectivityReceiverListener listener;

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();

        boolean isConnected = activeNetwork!=null && activeNetwork.isConnectedOrConnecting();
        if(listener!=null){
        listener.onNetworkConnectionChanged(isConnected);
        }

    }

   public static boolean isConnected(){
        ConnectivityManager manager = (ConnectivityManager) MyApp.getInstance()
                                            .getApplicationContext()
                                            .getSystemService(Context.CONNECTIVITY_SERVICE);
       NetworkInfo activeNetwork = manager.getActiveNetworkInfo();

       return activeNetwork!=null && activeNetwork.isConnectedOrConnecting();
    }

    public interface ConnectivityReceiverListener{
        void onNetworkConnectionChanged(boolean isConnected);
    }

}
