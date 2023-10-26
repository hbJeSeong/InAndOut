package com.jshwangbo.inandout.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.jshwangbo.inandout.R;
import com.jshwangbo.inandout.util.INOConstants;

public class StatisticsActivity extends AppCompatActivity implements MyWidget{

    static String TAG = "INO-StatisticsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initWidget() {

    }
}