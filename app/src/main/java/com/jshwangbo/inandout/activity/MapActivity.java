package com.jshwangbo.inandout.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.jshwangbo.inandout.R;


public class MapActivity extends AppCompatActivity {

    static String TAG = "INO-MapActivity";
    public MapActivity mapActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Log.d(TAG, "call onCreate()");
        mapActivity = this;
    }
}