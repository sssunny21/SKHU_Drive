package com.jhjg.skhu_drive.Controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.jhjg.skhu_drive.Model.UserData;
import com.jhjg.skhu_drive.R;
import com.jhjg.skhu_drive.Support.Address;
import com.jhjg.skhu_drive.View.LoginActivity;
import com.loopj.android.http.AsyncHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;

import cz.msebera.android.httpclient.Consts;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

/**
 * Created by kangjungu1 on 2016. 5. 2..
 */
public class JoinHelper {

    public static final String TAG = "JoinHelper";
    private String path;

    private static JoinHelper ourInstance = new JoinHelper();

    private EditText passwordInput, idInput, nameInput, phoneInput, emailInput, passwordQ, passwordA;
    private String[] join;
    private MaterialDialog dialog;
    private RadioButton gradeButton1, gradeButton2, gradeButton3, gradeButton4;
    private Spinner majorSpinner;

    private ProgressDialog progressDialog;
    private Header[] headers;
    private Vector<NameValuePair> nameValue;
    private cz.msebera.android.httpclient.client.methods.HttpPost request;
    private HttpEntity entity;
    private AsyncHttpClient httpClient;
    private InputStream im;
    private BufferedReader reader;
    private String total, tmp;
    private UserData data;

    private Context loginContext;

    public static JoinHelper getInstance() {
        return ourInstance;
    }

    private JoinHelper() {
        path = Address.getInstance().getJoinURL();
    }

    public void sendJoin(Context context) {
        loginContext = context;
        showJoinDialog();
    }


    private void showJoinDialog() {

        //회원가입 변수 string 선언
        join = new String[9];

        //첫번째 다이얼로그
        dialog = new MaterialDialog.Builder(loginContext)
                .title("회원가입")
                .customView(R.layout.join_custom_view, true)
                .negativeText("취소")
                .positiveText("가입")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {


                        join[0] = idInput.getText().toString();
                        join[1] = passwordInput.getText().toString();
                        join[2] = nameInput.getText().toString();
                        join[3] = phoneInput.getText().toString();
                        join[4] = emailInput.getText().toString();

                        //학년버튼에따라 학년 넣어줌
                        if (gradeButton1.isChecked()) {
                            join[5] = "1";
                        } else if (gradeButton2.isChecked()) {
                            join[5] = "2";
                        } else if (gradeButton3.isChecked()) {
                            join[5] = "3";
                        } else if (gradeButton4.isChecked()) {
                            join[5] = "4";
                        }

                        //비밀번호 Q&A
                        join[6] = passwordA.getText().toString();
                        join[7] = passwordQ.getText().toString();

                        for (int i = 0; i < join.length; i++)
                            Log.e(TAG, "join" + i + " " + join[i] + "\n");

                        JoinPost post = new JoinPost();
                        post.execute(join);

                    }
                }).build();



        //id와 password input 값을 가져오기 위해 선언
        idInput = (EditText) dialog.getCustomView().findViewById(R.id.input_id);
        passwordInput = (EditText) dialog.getCustomView().findViewById(R.id.input_password);
        nameInput = (EditText) dialog.getCustomView().findViewById(R.id.input_name);
        phoneInput = (EditText) dialog.getCustomView().findViewById(R.id.input_phoneNumber);
        emailInput = (EditText) dialog.getCustomView().findViewById(R.id.input_email);

        gradeButton1 = (RadioButton) dialog.getCustomView().findViewById(R.id.input_grade1);
        gradeButton2 = (RadioButton) dialog.getCustomView().findViewById(R.id.input_grade2);
        gradeButton3 = (RadioButton) dialog.getCustomView().findViewById(R.id.input_grade3);
        gradeButton4 = (RadioButton) dialog.getCustomView().findViewById(R.id.input_grade4);
        passwordA = (EditText) dialog.getCustomView().findViewById(R.id.input_passwordA);
        passwordQ = (EditText) dialog.getCustomView().findViewById(R.id.input_passwordQ);
        majorSpinner = (Spinner) dialog.getCustomView().findViewById(R.id.input_major);


        //
        if(UserDBManager.getInstance().getUserData(0) != null){
            Log.e(TAG,"1");
            data = UserDBManager.getInstance().getUserData(0);

            idInput.setText(data.getU_id());
            nameInput.setText(data.getU_name());
            phoneInput.setText(data.getU_tel());
            emailInput.setText(data.getU_email());
            passwordA.setText(data.getU_qpw());

        }else{
            Log.e(TAG,"2");
        }

        //spinner adapter
        ArrayAdapter adapter = ArrayAdapter.createFromResource(loginContext,R.array.major,R.layout.spinner_item);

        majorSpinner.setAdapter(adapter);

        //학과 선택
        majorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                join[8] = position + 1 + "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dialog.show();
    }

    private class JoinPost extends AsyncTask<String, String, Integer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = progressDialog.show(loginContext, "회원가입중", "회원가입중");
            data = new UserData(join);
            data.setA_Id(0);
            UserDBManager.getInstance().update(data);
        }

        @Override
        protected Integer doInBackground(String... params) {
            try {

                //db에 회원가입 정보 저장



                request = new cz.msebera.android.httpclient.client.methods.HttpPost(path);
                //전달할 인자들
                nameValue = new Vector<NameValuePair>();

                //mb_id, mb_password에는 jsp에 있는 파라미터 값을 넣어줌
                //첫번째 params가 id, 2번째 params가 password
                nameValue.add(new BasicNameValuePair("u_id", params[0]));
                nameValue.add(new BasicNameValuePair("u_pw", params[1]));
                nameValue.add(new BasicNameValuePair("u_name", params[2]));
                nameValue.add(new BasicNameValuePair("u_tel", params[3]));
                nameValue.add(new BasicNameValuePair("u_email", params[4]));
                nameValue.add(new BasicNameValuePair("u_grade", params[5]));
                nameValue.add(new BasicNameValuePair("u_qpw", params[6]));
                nameValue.add(new BasicNameValuePair("u_apw", params[7]));
                nameValue.add(new BasicNameValuePair("d_id", params[8]));



                //웹 접속 - utf-8 방식으로
                entity = new UrlEncodedFormEntity(nameValue, Consts.UTF_8);
                //속성넣기
                request.setEntity(entity);

                httpClient = new AsyncHttpClient();


                HttpResponse res = httpClient.getHttpClient().execute(request);

                //웹 서버 값받기
                HttpEntity entityResponse = res.getEntity();


//                헤더가져오기
                headers = res.getAllHeaders();

                //로그인 실패시 -1 리턴
                for (int j = 0; j < headers.length; j++) {
                    Log.e(TAG, "header " + j + " " + headers[j].getName() + ", " + headers[j].getValue());
                    if (headers[j].getValue().contains("error")) {
                        return -1;
                    }

                }
//                서버에서 보내온 자료가져오기
                im = entityResponse.getContent();
                reader = new BufferedReader(new InputStreamReader(im, Consts.UTF_8));

                total = "";
                tmp = "";

                //버퍼에있는거 전부 더해주기
                //readLine -> 파일내용을 줄 단위로 읽기
                while ((tmp = reader.readLine()) != null) {
                    if (tmp != null) {
                        total += tmp;
                    }
                }
                im.close();
                //결과창뿌려주기 - ui 변경시 에러
                Log.e(TAG, "total " + total);

                if(total.contains("저장했습니다")){
                    return 0;
                }else if(total.contains("중복")){
                    Log.e(TAG,"id : "+params[0]);
                    return -2;
                }else{
                    return -1;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }


            //오류시 null 반환
            return -1;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            //로그인 프로그레스를끈다.
            progressDialog.dismiss();
            dialog.dismiss();
            Log.e(TAG,"integer"+integer);

            if(integer == -2){
                Snackbar.make( ((LoginActivity) loginContext).findViewById(R.id.login_activity), "아이디가 중복됩니다.", Snackbar.LENGTH_LONG).show();
            }else if(integer == 0){
                Snackbar.make( ((LoginActivity) loginContext).findViewById(R.id.login_activity), "가입 성공", Snackbar.LENGTH_LONG).show();
                UserDBManager.getInstance().delete(0);
            }else{
                Snackbar.make( ((LoginActivity) loginContext).findViewById(R.id.login_activity), "가입 실패입니다.", Snackbar.LENGTH_LONG).show();
            }
        }

    }
}
