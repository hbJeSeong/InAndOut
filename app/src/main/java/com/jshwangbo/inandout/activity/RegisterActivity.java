package com.jshwangbo.inandout.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jshwangbo.inandout.R;
import com.jshwangbo.inandout.util.INOConstants;

public class RegisterActivity extends AppCompatActivity implements MyWidget{

    static String TAG = INOConstants.TAG_REGISTERACTIVITY;
    public AppCompatButton btnSubmit;
    public EditText editTextId;
    public EditText editTextPw;
    public EditText editTextRePw;
    private RegisterActivity registerActivity;

    @Override
    public void onClick(View v) {
        Intent iTargetActivity = null;

        if (v.getId() == R.id.button_submit) {
            if(isValidStateForSubmit()){
                if (isPasswordValid(editTextPw.getText().toString(), editTextRePw.getText().toString())) {
                    new AlertDialog.Builder(this)
                            .setTitle("REGISETER")
                            .setMessage("Are You Sure?")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.d(TAG, "User Select \"OK\"");
                                    editTextId.setText("");
                                    editTextPw.setText("");
                                    editTextRePw.setText("");
                                    MainActivity.bIsRegistered = true;
                                    finish();
                                }
                            })
                            .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.d(TAG, "User Select \"CANCEL\"");
                                    editTextPw.setText("");
                                    editTextRePw.setText("");
                                    Toast.makeText(registerActivity, "", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .create()
                            .show();
                } else {
                    Toast.makeText(this, "Please Check Password", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(this, "Please Enter ID and PW", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void initWidget() {
        this.btnSubmit = (AppCompatButton) findViewById(R.id.button_submit);
        this.editTextId     = (EditText) findViewById(R.id.edittxt_id);
        this.editTextPw     = (EditText) findViewById(R.id.edittxt_password);
        this.editTextRePw = (EditText) findViewById(R.id.edittxt_rePassword);

        btnSubmit.setOnClickListener(this);
        editTextId.setOnClickListener(this);
        editTextPw.setOnClickListener(this);
        editTextRePw.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerActivity = this;
        initWidget();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private boolean isPasswordValid(String arg1, String arg2){
        return arg1.equals(arg2);
    }

    private boolean isValidStateForSubmit(){
        return !editTextId.getText().toString().isEmpty() && !editTextPw.getText().toString().isEmpty() && !editTextRePw.getText().toString().isEmpty();
    }

}