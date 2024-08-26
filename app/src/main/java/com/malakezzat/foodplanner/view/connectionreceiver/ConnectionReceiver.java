package com.malakezzat.foodplanner.view.connectionreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionReceiver extends BroadcastReceiver {

    ConnectionListener connectionListener;

    public ConnectionReceiver(ConnectionListener connectionListener){
        this.connectionListener = connectionListener;
    }
    @Override
    public void onReceive(Context context, Intent intent) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        boolean isConnected = false;
        if (activeNetwork != null){

            if (activeNetwork.getState() == NetworkInfo.State.CONNECTING || activeNetwork.getState() == NetworkInfo.State.DISCONNECTING){
                return;
            }
            isConnected = activeNetwork.isConnected();
        }

        if (connectionListener != null) {
            connectionListener.onChangeConnection(isConnected);
        }
    }

}