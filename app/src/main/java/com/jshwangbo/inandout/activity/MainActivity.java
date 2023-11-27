package com.jshwangbo.inandout.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jshwangbo.inandout.R;
import com.jshwangbo.inandout.util.Callbacks;

public class MainActivity extends AppCompatActivity implements MyWidget{

    static String TAG = "INO-MainActivity";
    public static MainActivity mainActivity;
    public static NetworkReceiver networkReceiver;

    private AppCompatButton btnLog;
    private AppCompatButton btnMap;
    private AppCompatButton btnStatistics;
    private AppCompatButton btnRegister;
    private AppCompatButton btnG2h;
    private AppCompatButton btnG2w;
    private ImageView imgBtnReload;
    private ImageView imgHome;
    private ImageView imgStatus;
    private ImageView imgCompany;
    private TextView txtStatus;

    public static boolean bIsRegistered = false;
    public static boolean bIsConnectedNetwork = false;

    @Override
    public void onClick(View v) {
        Intent iTargetActivity = null;

    }

    @Override
    public void initWidget() {
        btnRegister     = (AppCompatButton) mainActivity.findViewById(R.id.button_register);
        btnLog          = (AppCompatButton) mainActivity.findViewById(R.id.button_log);
        btnMap          = (AppCompatButton) mainActivity.findViewById(R.id.button_map);
        btnStatistics   = (AppCompatButton) mainActivity.findViewById(R.id.button_statistics);
        btnG2h          = (AppCompatButton) mainActivity.findViewById(R.id.button_gh);
        btnG2w          = (AppCompatButton) mainActivity.findViewById(R.id.button_gtw);

        imgBtnReload    = (ImageView) mainActivity.findViewById(R.id.img_button_reload);
        imgHome         = (ImageView) mainActivity.findViewById(R.id.img_home);
        imgCompany      = (ImageView) mainActivity.findViewById(R.id.img_company);
        imgStatus       = (ImageView) mainActivity.findViewById(R.id.img_status);

        btnStatistics.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        btnLog.setOnClickListener(this);
        btnMap.setOnClickListener(this);
        btnG2h.setOnClickListener(this);
        btnG2w.setOnClickListener(this);
        imgBtnReload.setOnClickListener(this);

        imgCompany.setImageResource(R.drawable.icon_company_disable);
        imgHome.setImageResource(R.drawable.icon_home_disable);

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

//    public static void networkConnect(Callbacks cb){
//        if(bIsConnectedNetwork) {
//            cb.notifyEvt(INOConstants.ACTION_NETWORK_CONNECT, new Callbacks.cbArg(INOConstants.NETWORK_STATE_CONNECTED));
//        } else {
//            cb.notifyEvt(INOConstants.ACTION_NETWORK_CONNECT, new Callbacks.cbArg(INOConstants.NETWORK_STATE_DISCONNECTED));
//        }
//    }
//
//    public static void setNetworkState(final String str){
//        if(str.equals(INOConstants.NETWORK_STATE_CONNECTED)) {
//            Log.d(TAG, ":: setNetworkState :: Set Network State : " + String.format("[ %s ]", INOConstants.NETWORK_STATE_CONNECTED));
//            bIsConnectedNetwork = true;
//
//        } else {
//            Log.d(TAG, ":: setNetworkState :: Set Network State : " + String.format("[ %s ]", INOConstants.NETWORK_STATE_DISCONNECTED));
//            bIsConnectedNetwork = false;
//        }
//    }

    public static class NetworkReceiver extends BroadcastReceiver {

        public Context context;
        public NetworkReceiver(Context context){
            this.context = context;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, ":: NetworkReceiver :: onReceive :: " + intent.getAction() );
//            networkConnect(new Callbacks() {
//                @Override
//                public void notifyEvt(String str, cbArg arg) {
//                    if (NetworkUtil.isConnected(context)) {
//                        setNetworkState(INOConstants.NETWORK_STATE_CONNECTED);
//                        Log.d(TAG, ":: NetworkReceiver :: notifyEvt :: Network is Connected");
//                        Toast.makeText(context, "네트워크가 정상적으로 연결되었습니다.", Toast.LENGTH_SHORT).show();
//                    } else {
//                        setNetworkState(INOConstants.NETWORK_STATE_DISCONNECTED);
//                        Log.d(TAG, ":: NetworkReceiver :: notifyEvt :: Network is Disconnected");
//                        Toast.makeText(context, "네트워크 연결이 끊어졌습니다.", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
        }
    }

}