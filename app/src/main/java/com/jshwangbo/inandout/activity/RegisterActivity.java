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

public class RegisterActivity extends AppCompatActivity implements MyWidget{

    static String TAG = "INO-RegisterActivity";
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
                String sPassword1 = editTextPw.getText().toString();
                String sPassword2 = editTextRePw.getText().toString();
                if (SecurityUtil.isPasswordCorrect(sPassword1, sPassword2)) {
                    new AlertDialog.Builder(this)
                            .setTitle("사용자 등록")
                            .setMessage("진행하시겠습니까?")
                            .setPositiveButton("네", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.d(TAG, ":: onClick :: Selected Item is \"YES\"");
                                    editTextId.setText("");
                                    editTextPw.setText("");
                                    editTextRePw.setText("");
                                    MainActivity.bIsRegistered = true;
                                    finish();
                                }
                            })
                            .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.d(TAG, ":: onClick :: Selected Item is \"NO\"");
                                    editTextPw.setText("");
                                    editTextRePw.setText("");
                                    Toast.makeText(registerActivity, "", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .create()
                            .show();
                } else {
                    Toast.makeText(this, "비밀번호를 다시 확인해주세요", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(this, "ID와 PW를 입력해주세요.", Toast.LENGTH_SHORT).show();
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

    private boolean isValidStateForSubmit(){
        return !editTextId.getText().toString().isEmpty() && !editTextPw.getText().toString().isEmpty() && !editTextRePw.getText().toString().isEmpty();
    }

}