package com.example.imdb.broadcast.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.Toast;

import com.example.imdb.model.NetworkUtil;




public class InternetBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, final Intent intent) {

        String status = NetworkUtil.getConnectivityStatusString(context);

        Log.d("network",status);

        Toast.makeText(context, status, Toast.LENGTH_SHORT).show();
    }
}
