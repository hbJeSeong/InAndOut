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
import com.jshwangbo.inandout.util.INOConstants;
import com.jshwangbo.inandout.util.SecurityUtil;
import com.jshwangbo.inandout.util.UserStatus;
import com.jshwangbo.inandout.util.NetworkUtil;

public class MainActivity extends AppCompatActivity implements MyWidget{

    static String TAG = "INO-MainActivity";
    public static MainActivity mainActivity;
    public static NetworkReceiver networkReceiver;

    public static UserStatus userStatus;

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

            } else if (v.getId() == R.id.img_button_reload){
                Toast.makeText(mainActivity, "새로고침 요청", Toast.LENGTH_SHORT).show();
                Animation animation = AnimationUtils.loadAnimation(mainActivity.getApplicationContext(), R.anim.anim_reload);
                v.startAnimation(animation);

                this.imgStatus.setAnimation(null);
                this.imgStatus.setVisibility(View.INVISIBLE);
                this.imgCompany.setImageResource(R.drawable.icon_company_disable);
                this.imgHome.setImageResource(R.drawable.icon_home_disable);

            } else if (v.getId() == R.id.button_gh){
                Log.d(TAG, ":: CASE :: \"Go Home\"");

                String sStatusTmp = userStatus.getUserStatus();
                if (sStatusTmp.equals(INOConstants.USER_STATUS_100) || sStatusTmp.equals(INOConstants.USER_STATUS_102)){
                    Toast.makeText(mainActivity, "잘못된 요청입니다. 현재 상태 " + sStatusTmp, Toast.LENGTH_SHORT).show();
                    Log.d(TAG, ":: CASE :: \"Go Home\" Wrong Request");
                } else if (sStatusTmp.equals(INOConstants.USER_STATUS_101)){
                    imgStatus.clearAnimation();
                    imgStatus.setVisibility(View.INVISIBLE);
                    imgHome.setImageResource(R.drawable.icon_home);
                    imgCompany.setImageResource(R.drawable.icon_company_disable);
                    Toast.makeText(mainActivity, "퇴근 완료!!", Toast.LENGTH_SHORT).show();
                    userStatus.setsUserStatus(INOConstants.USER_STATUS_102);
                    Log.d(TAG, ":: CASE :: \"Go Home\" DONE");
                } else {
                    this.imgStatus.setVisibility(View.VISIBLE);

                    Animation animation = AnimationUtils.loadAnimation(mainActivity.getApplicationContext(), R.anim.anim_g2h);

                    imgHome.setImageResource(R.drawable.icon_home);
                    imgCompany.setImageResource(R.drawable.icon_company_disable);
                    imgStatus.setImageResource(R.drawable.icon_r2l);

                    imgStatus.startAnimation(animation);
                    userStatus.setsUserStatus(INOConstants.USER_STATUS_101);
                    Log.d(TAG, ":: CASE :: \"Go Home\" Change User Status");
                }

            } else if (v.getId() == R.id.button_gtw){
                Log.d(TAG, ":: CASE :: \"Go To Work\"");

                String sStatusTmp = userStatus.getUserStatus();
                if (sStatusTmp.equals(INOConstants.USER_STATUS_103) || sStatusTmp.equals(INOConstants.USER_STATUS_101)){
                    Log.d(TAG, ":: CASE :: \"Go To Work\" Wrong Request");
                    Toast.makeText(mainActivity, "잘못된 요청입니다. 현재 상태 " + sStatusTmp, Toast.LENGTH_SHORT).show();
                } else if (sStatusTmp.equals(INOConstants.USER_STATUS_100)) {
                    imgStatus.clearAnimation();
                    imgStatus.setVisibility(View.INVISIBLE);
                    imgHome.setImageResource(R.drawable.icon_home_disable);
                    imgCompany.setImageResource(R.drawable.icon_company);
                    Toast.makeText(mainActivity, "출근 완료!!", Toast.LENGTH_SHORT).show();
                    userStatus.setsUserStatus(INOConstants.USER_STATUS_103);
                    Log.d(TAG, ":: CASE :: \"Go To Work\" DONE");
                } else {
                    this.imgStatus.setVisibility(View.VISIBLE);

                    Animation animation = AnimationUtils.loadAnimation(mainActivity.getApplicationContext(), R.anim.anim_g2w);
                    imgHome.setImageResource(R.drawable.icon_home_disable);
                    imgCompany.setImageResource(R.drawable.icon_company);
                    imgStatus.setImageResource(R.drawable.icon_l2r);

                    imgStatus.startAnimation(animation);
                    userStatus.setsUserStatus(INOConstants.USER_STATUS_100);
                    Log.d(TAG, ":: CASE :: \"Go To Work\" Change User Status");
                }
            } else {
                iTargetActivity = null;
                Log.d(TAG, "Wrong Button ID");
            }
            if(iTargetActivity == null) {
                Log.d(TAG, ":: iTargetActivity is Null Object");
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

        userStatus = new UserStatus();
//        userStatus.setsUserStatus(INOConstants.USER_STATUS_103);

        mainActivity        = this;
        networkReceiver     = new NetworkReceiver(this);
        initWidget();

//        Log.d(TAG, "GET UNIQUE VALUE :: " + Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));

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