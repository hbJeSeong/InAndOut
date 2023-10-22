package com.jshwangbo.inandout.util.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.jshwangbo.inandout.activity.MainActivity;
import com.jshwangbo.inandout.util.INOConstants;

public class NetworkUtil  {

    public static final String TAG = INOConstants.TAG_NETWORK_UTIL;

    public static boolean isConnected(Context context){
        Log.d(TAG, ":: isConnected() :: call");
        boolean result = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        result = networkInfo != null && networkInfo.isConnected();

        Log.d(TAG, String.format(":: isConnected() :: getValue :: ( %s )", String.valueOf(result)));
        return result;

    }
}
