package com.jshwangbo.inandout.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.usage.NetworkStatsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jshwangbo.inandout.R;
import com.jshwangbo.inandout.util.Callbacks;
import com.jshwangbo.inandout.util.INOConstants;
import com.jshwangbo.inandout.util.network.NetworkUtil;

public class MainActivity extends AppCompatActivity implements MyWidget{

    static String TAG = INOConstants.TAG_MAINACTIVITY;
    public static MainActivity mainActivity;
    public static NetworkReceiver networkReceiver;

    private AppCompatButton btnLog;
    private AppCompatButton btnMap;
    private AppCompatButton btnStatistics;
    private AppCompatButton btnRegister;

    public static boolean bIsRegistered = false;
    public static boolean bIsConnectedNetwork = false;

    @Override
    public void onClick(View v) {
        Intent iTargetActivity = null;

        try{
            if(v.getId() == R.id.button_log){
                iTargetActivity = new Intent(mainActivity.getApplicationContext(), LogActivity.class);
            } else if(v.getId() == R.id.button_map) {
                iTargetActivity = new Intent(mainActivity.getApplicationContext(), MapActivity.class);
            } else if(v.getId() == R.id.button_statistics) {
                iTargetActivity = new Intent(mainActivity.getApplicationContext(), StatisticsActivity.class);
            } else if(v.getId() == R.id.button_register) {
                if(bIsConnectedNetwork) {
                    if (!bIsRegistered) {
                        iTargetActivity = new Intent(mainActivity.getApplicationContext(), RegisterActivity.class);
                    } else {
                        new AlertDialog.Builder(this)
                                .setTitle("사용자 등록")
                                .setMessage("사용자가 이미 등록되어 있습니다. 등록 정보를 삭제하시겠습니까?")
                                .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Log.d(TAG, ":: AlertDialog :: User Select \"OK\"");
                                        Toast.makeText(mainActivity, "사용자 삭제 요청이 완료 되었습니다.", Toast.LENGTH_SHORT).show();
                                        bIsRegistered = false;
                                    }
                                })
                                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Log.d(TAG, ":: AlertDialog :: User Select \"NO\"");
                                        bIsRegistered = true;
                                    }
                                })
                                .create()
                                .show();
                    }
                } else {
                    Toast.makeText(mainActivity, "네트워크 연결 확인이 필요합니다.", Toast.LENGTH_SHORT).show();
                }

            } else {
                iTargetActivity = null;
                Log.d(TAG, "Wrong Button ID");
            }

            if(iTargetActivity == null) {
                Log.d(TAG, "iTargetActivity is Null Object");
            } else {
                startActivity(iTargetActivity);
            }

        } catch (Exception e){
            e.printStackTrace();
            Log.d(TAG, "Exception Occur");
        }
    }

    @Override
    public void initWidget() {
        btnRegister   = (AppCompatButton) mainActivity.findViewById(R.id.button_register);
        btnLog        = (AppCompatButton) mainActivity.findViewById(R.id.button_log);
        btnMap        = (AppCompatButton) mainActivity.findViewById(R.id.button_map);
        btnStatistics = (AppCompatButton) mainActivity.findViewById(R.id.button_statistics);

        btnStatistics.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        btnLog.setOnClickListener(this);
        btnMap.setOnClickListener(this);

        mainActivity.registerReceiver(networkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivity        = this;
        networkReceiver     = new NetworkReceiver(this);
        initWidget();
    }

    public static void networkConnect(Callbacks cb){
        if(bIsConnectedNetwork) {
            cb.notifyEvt(INOConstants.ACTION_NETWORK_CONNECT, new Callbacks.cbArg(INOConstants.NETWORK_STATE_CONNECTED));
        } else {
            cb.notifyEvt(INOConstants.ACTION_NETWORK_CONNECT, new Callbacks.cbArg(INOConstants.NETWORK_STATE_DISCONNECTED));
        }
    }

    public static void setNetworkState(final String str){
        if(str.equals(INOConstants.NETWORK_STATE_CONNECTED)) {
            Log.d(TAG, ":: setNetworkState :: Set Network State : " + String.format("[ %s ]", INOConstants.NETWORK_STATE_CONNECTED));
            bIsConnectedNetwork = true;

        } else {
            Log.d(TAG, ":: setNetworkState :: Set Network State : " + String.format("[ %s ]", INOConstants.NETWORK_STATE_DISCONNECTED));
            bIsConnectedNetwork = false;
        }
    }

    public static class NetworkReceiver extends BroadcastReceiver {

        public Context context;
        public NetworkReceiver(Context context){
            this.context = context;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, ":: NetworkReceiver :: onReceive :: " + intent.getAction() );
            networkConnect(new Callbacks() {
                @Override
                public void notifyEvt(String str, cbArg arg) {
                    if (NetworkUtil.isConnected(context)) {
                        setNetworkState(INOConstants.NETWORK_STATE_CONNECTED);
                        Log.d(TAG, ":: NetworkReceiver :: notifyEvt :: Network is Connected");
                        Toast.makeText(context, "네트워크가 정상적으로 연결되었습니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        setNetworkState(INOConstants.NETWORK_STATE_DISCONNECTED);
                        Log.d(TAG, ":: NetworkReceiver :: notifyEvt :: Network is Disconnected");
                        Toast.makeText(context, "네트워크 연결이 끊어졌습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}