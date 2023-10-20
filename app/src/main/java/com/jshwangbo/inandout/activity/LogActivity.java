package com.jshwangbo.inandout.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.jshwangbo.inandout.BuildConfig;
import com.jshwangbo.inandout.R;
import com.jshwangbo.inandout.util.INOConstants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LogActivity extends AppCompatActivity implements MyWidget {

    static String TAG = INOConstants.TAG_LOGACTIVITY;
    public AppCompatButton btnDate;
    public AppCompatButton btnLoad;
    public CheckBox ckBoxDate;
    public TextView txtViewDate;
    public static String date = null;
    Toast toast = null;
    private boolean bIsBoxChecked = true;

    final String INIT_DATE = "0000-00-00";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        bIsBoxChecked = true;

        Log.d(TAG, "MAIN SERVER IP IS " + BuildConfig.MAIN_SERVER_IP);
        initWidget();
    }

    @Override
    public void onClick(View v) {

        Calendar calendar = Calendar.getInstance();
        int year    = calendar.get(Calendar.YEAR);
        int month   = calendar.get(Calendar.MONTH);
        int day     = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(LogActivity.this, new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                date = String.format("%d-%02d-%02d", year, month, dayOfMonth);
                txtViewDate.setText(date);
            }
        }, year, month, day);

        if (v.getId() == R.id.button_select_date) {
            datePickerDialog.show();
        } else if (v.getId() == R.id.checkbox_date) {

            if (!this.ckBoxDate.isChecked()) {
                bIsBoxChecked = false;
                Toast.makeText(this, "If you want Specific Day's Data, then Check", Toast.LENGTH_SHORT).show();
                this.btnDate.setClickable(false);
            } else {
                bIsBoxChecked = true;
                Toast.makeText(this, "If you want all Data, then Uncheck", Toast.LENGTH_SHORT).show();
                this.btnDate.setClickable(true);
            }

        } else if (v.getId() == R.id.button_loadData) {
            String tmp = null;

            if(bIsBoxChecked) {
                if(date.equals(INIT_DATE) || date == null) {
                    tmp = "Please Check The Date";
                } else {
                    tmp = "Please Wait, Get \'" + date + "\'s Data From Server";
                }
            } else {
                tmp = "Please Wait, Get All Data From Server";
            }

            Toast.makeText(this, tmp, Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void initWidget() {
        this.btnDate     = (AppCompatButton) findViewById(R.id.button_select_date);
        this.btnLoad     = (AppCompatButton) findViewById(R.id.button_loadData);
        this.txtViewDate = (TextView) findViewById(R.id.textview_date);
        this.ckBoxDate   = (CheckBox) findViewById(R.id.checkbox_date);

        this.btnDate.setClickable(true);
        this.ckBoxDate.setChecked(bIsBoxChecked);

        try {
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date now = new Date();
            date = dateFormat.format(now);
            txtViewDate.setText(date);

            Log.d(TAG, "Get Current Date " + date);

        } catch (Exception e){
            date = INIT_DATE;
            txtViewDate.setText(date);
            e.printStackTrace();
        }

        this.btnDate.setOnClickListener(this);
        this.btnLoad.setOnClickListener(this);
        this.ckBoxDate.setOnClickListener(this);

    }
}