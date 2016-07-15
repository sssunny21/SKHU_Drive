package com.jhjg.skhu_drive.Controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.util.Log;

import com.jhjg.skhu_drive.R;
import com.jhjg.skhu_drive.Support.Address;
import com.jhjg.skhu_drive.Support.MyApplication;
import com.jhjg.skhu_drive.View.LoginActivity;
import com.jhjg.skhu_drive.View.MainActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;

import java.io.IOException;
import java.util.Vector;

import cz.msebera.android.httpclient.Consts;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.impl.cookie.BasicClientCookie;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

/**
 * Created by kangjungu1 on 2016. 4. 25..
 */
public class LoginHelper {

    public static final String TAG = "LoginHelper";
    private String path;

    private static LoginHelper ourInstance = new LoginHelper();

    public static LoginHelper getInstance() {
        return ourInstance;
    }

    private String id;
    private String password;
    private Header cookie;
    private Header[] headers;
    private Vector<NameValuePair> nameValue;
    private cz.msebera.android.httpclient.client.methods.HttpPost request;
    private HttpEntity entity;
    private AsyncHttpClient httpClient;
    private PersistentCookieStore myCookieStore;
    private BasicClientCookie newCookie;
    private Context loginContext;
    private Intent intent;
    private ProgressDialog progressdi;

    private LoginHelper() {
        path = Address.getInstance().getLoginURL();
    }

    public void sendLogin(Context context,String[] login) {

        loginContext = context;
        LoginPost post = new LoginPost();
        post.execute(login);

    }


    private class LoginPost extends AsyncTask<String, String, Integer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressdi = progressdi.show(loginContext, "로그인 중", "Loading ...");
        }

        @Override
        protected Integer doInBackground(String... params) {
            //첫번째 param : 주소
            try {
                //1~3번째 인자에 path, id, password에 넣어줌
                id = params[0];
                password = params[1];

                Log.e(TAG,"path "+path);
                request = new cz.msebera.android.httpclient.client.methods.HttpPost(path);
                //전달할 인자들
                nameValue = new Vector<NameValuePair>();

                //mb_id, mb_password에는 jsp에 있는 파라미터 값을 넣어줌
                //첫번째 params가 id, 2번째 params가 password
                nameValue.add(new BasicNameValuePair("u_id", id));
                nameValue.add(new BasicNameValuePair("u_pw", password));

                //웹 접속 - utf-8 방식으로
                entity = new UrlEncodedFormEntity(nameValue, Consts.UTF_8);
                //속성넣기
                request.setEntity(entity);

                httpClient = new AsyncHttpClient();

                //////////////////////////////////////// 쿠키스토어
                myCookieStore = new PersistentCookieStore(MyApplication.getContet());
                httpClient.setCookieStore(myCookieStore);


                HttpResponse res = httpClient.getHttpClient().execute(request);

                //쿠키 가져오기
                cookie = res.getFirstHeader("Set-Cookie");

                //로그인 실패시 -1 리턴
                if (cookie == null) {
                    return -1;
                }

                Log.e(TAG, "cookie " + cookie);

                //쿠키 저장
                newCookie = new BasicClientCookie(cookie.getName(), cookie.getValue());
                newCookie.setVersion(0);
                newCookie.setDomain(Address.getInstance().getDomain());
                newCookie.setPath("/drive/");
                myCookieStore.addCookie(newCookie);

                //헤더가져오기
                headers = res.getAllHeaders();
                Log.e(TAG,"header length : "+headers.length);
                //로그인 실패시 -1 리턴
                for (int j = 0; j < headers.length; j++) {
                    Log.e(TAG, "header " + j + " " + headers[j].getName() + ", " + headers[j].getValue());
                    if (headers[j].getValue().contains("error")) {
                        return -1;
                    }

                }

                return 0;

            } catch (IOException e) {
                e.printStackTrace();
            }


            //오류시 null 반환
            return -1;
        }

        //asyonTask 3번째 인자와 일치 매개변수값 -> doInBackground 리턴값이 전달됨
        //AsynoTask 는 preExcute - doInBackground - postExecute 순으로 자동으로 실행됩니다.
        //ui는 여기서 변경

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            progressdi.dismiss();

            Log.e(TAG, "integer" + integer);
            if (integer == -1) {
                Log.e(TAG, "로그인실패");
                //로그인실패시 다시 입력받는다.
                Snackbar.make( ((LoginActivity) loginContext).findViewById(R.id.login_activity), "로그인 실패", Snackbar.LENGTH_LONG).show();

            } else {
                //로그인 성공
                intent = new Intent(loginContext, MainActivity.class);
                //id 넣어서 전달
                intent.putExtra("id",id);
                loginContext.startActivity(intent);
            }
        }

    }

}
