package com.jhjg.skhu_drive.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import com.jhjg.skhu_drive.Controller.SettingManager;
import com.jhjg.skhu_drive.R;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private android.widget.CheckBox googleCheck;
    private Intent intent;
    private boolean checkBoolean;
    private static final int REQUEST_SUCCESS = 1;
    private final String GOOGLE_CHECKED_KEY = "googleCheck";
    private final String PREF_ACCOUNT_NAME = "accountName";
    public static final String TAG = "SettingActivity";
    private android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.googleCheck = (CheckBox) findViewById(R.id.setting_checkBox);

        toolbar.setTitle("Settings");

        //setting 값 가져오기
        checkBoolean = SettingManager.getInstance().getBoolean(GOOGLE_CHECKED_KEY);
        //체크 값 설정
        googleCheck.setChecked(checkBoolean);

        //클릭리스너
        googleCheck.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.setting_checkBox) {
            if (!googleCheck.isChecked()) {
                //체크 되어 있을경우.
                Log.e(TAG, "isChecked " + googleCheck.isChecked());
                SettingManager.getInstance().setBoolean(GOOGLE_CHECKED_KEY, false);
                SettingManager.getInstance().remove(PREF_ACCOUNT_NAME);
            } else {
                //체크 되어 있지 않을 경우.
                Log.e(TAG, "not checked " + googleCheck.isChecked());
                intent = new Intent(this, GoogleDriveActivity.class);
                startActivityForResult(intent, REQUEST_SUCCESS);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(TAG, "request " + requestCode);
        Log.e(TAG, "resultCode " + resultCode);

        switch (requestCode) {
            case REQUEST_SUCCESS:
                //성공적으로 연결되었을 시
                //check값 저장
                if (resultCode == RESULT_OK) {
                    Snackbar.make((this.findViewById(R.id.setting_activity)), "연결 완료", Snackbar.LENGTH_LONG).show();
                    SettingManager.getInstance().setBoolean(GOOGLE_CHECKED_KEY, true);
                } else {
                    googleCheck.setChecked(false);
                    Snackbar.make((this.findViewById(R.id.setting_activity)), "연결 실패", Snackbar.LENGTH_LONG).show();
                }
                break;
        }
    }
}
