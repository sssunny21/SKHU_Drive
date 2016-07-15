package com.jhjg.skhu_drive.View;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.jhjg.skhu_drive.Controller.JoinHelper;
import com.jhjg.skhu_drive.Controller.LoginHelper;
import com.jhjg.skhu_drive.R;
import com.rengwuxian.materialedittext.MaterialEditText;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivity";
    private com.rengwuxian.materialedittext.MaterialEditText loginid;
    private com.rengwuxian.materialedittext.MaterialEditText loginpassword;
    private com.dd.processbutton.iml.ActionProcessButton loginaction;
    private android.support.v7.widget.AppCompatTextView loginjoin;
    private android.widget.LinearLayout loginactivity;
    private String[] login;

    public final int MY_PERMISSION_REQUEST_STORAGE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //퍼미션체크
        checkPermission();

        this.loginactivity = (LinearLayout) findViewById(R.id.login_activity);
        this.loginjoin = (AppCompatTextView) findViewById(R.id.login_join);
        this.loginaction = (ActionProcessButton) findViewById(R.id.login_action);
        this.loginpassword = (MaterialEditText) findViewById(R.id.login_password);
        this.loginid = (MaterialEditText) findViewById(R.id.login_id);

        login = new String[2];

        //로그인 버튼 눌렀을때
        loginaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login[0] = loginid.getText().toString();
                login[1] = loginpassword.getText().toString();

                LoginHelper.getInstance().sendLogin(LoginActivity.this,login);

            }
        });

        loginjoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JoinHelper.getInstance().sendJoin(LoginActivity.this);
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Log.e(TAG,"permission was granted");
                } else {
                    Log.d(TAG, "Permission always deny");
                }
                break;
        }
    }
    
    /**
     * Permission check.
     */

    //TODO: 수정하기

    private void checkPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                Log.e(TAG,"test");
                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Toast.makeText(this, "Read/Write external storage", Toast.LENGTH_SHORT).show();
                }

                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSION_REQUEST_STORAGE);

                // MY_PERMISSION_REQUEST_STORAGE is an
                // app-defined int constant

            } else {
                // 다음 부분은 항상 허용일 경우에 해당이 됩니다.
                Log.e(TAG,"항상허용");
                return;
            }
        }
    }


}

