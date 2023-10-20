package com.jshwangbo.inandout.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jshwangbo.inandout.R;
import com.jshwangbo.inandout.util.INOConstants;

public class

MainActivity extends AppCompatActivity implements MyWidget{

    static String TAG = INOConstants.TAG_MAINACTIVITY;
    public static MainActivity mainActivity;

    private AppCompatButton btnLog;
    private AppCompatButton btnMap;
    private AppCompatButton btnStatistics;
    private AppCompatButton btnRegister;

    public static boolean bIsRegistered = false;

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

                if (!bIsRegistered) {
                    iTargetActivity = new Intent(mainActivity.getApplicationContext(), RegisterActivity.class);
                } else {
                    new AlertDialog.Builder(this)
                            .setTitle("UNREGISTER")
                            .setMessage("Are You Sure?")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.d(TAG, "User Select \"OK\"");
                                    Toast.makeText(mainActivity, "Complete to Unregister, Your Data in Server will be Deleted", Toast.LENGTH_SHORT).show();
                                    bIsRegistered = false;
                                }
                            })
                            .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.d(TAG, "User Select \"NO\"");
                                    bIsRegistered = true;
                                }
                            })
                            .create()
                            .show();
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivity = this;
        initWidget();
    }
}