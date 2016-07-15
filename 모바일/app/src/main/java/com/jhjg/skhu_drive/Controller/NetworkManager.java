package com.jhjg.skhu_drive.Controller;

import android.content.Context;
import android.support.annotation.Nullable;

import com.jhjg.skhu_drive.Support.Address;
import com.jhjg.skhu_drive.Support.MyApplication;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;

import cz.msebera.android.httpclient.cookie.Cookie;

/**
 * Created by kangjungu1 on 2016. 5. 16..
 */
public class NetworkManager {


    private static NetworkManager ourInstance = new NetworkManager();

    public static NetworkManager getInstance() {
        return ourInstance;
    }

    public static final String TAG = "ProfessorManager";


    private PersistentCookieStore myCookieStore;
    private Cookie storeCookie;
    private AsyncHttpClient client;
    private String url;
    private Context mContext;

    private NetworkManager() {
        myCookieStore = new PersistentCookieStore(MyApplication.getContet());
        storeCookie = myCookieStore.getCookies().get(0);
        client = new AsyncHttpClient();
        client.addHeader("Cookie", "JSESSIONID=" + storeCookie.getValue().substring(11, storeCookie.getValue().indexOf("; Path=/")));
    }

    // 리스트 받아올때 사용
    public void getList(BaseJsonHttpResponseHandler<JSONArray> handler, int type, @Nullable String[] parameterName,@Nullable String[] parameter) {
        RequestParams params = new RequestParams();

        if (parameterName != null && parameter != null) {
            for (int i = 0; i < parameterName.length; i++) {
                params.add(parameterName[i], parameter[i]);
            }
        }

        url = Address.getInstance().getURL(type);
        client.get(MyApplication.getContet(), url, params, handler);

    }

    //바이너리 데이터 -> 다운로드 받을때 사용
    public void getList(BinaryHttpResponseHandler handler, int type, String[] parameterName, String[] parameter) {
        RequestParams params = new RequestParams();

        for(int i=0;i<parameterName.length;i++){
            params.add(parameterName[i], parameter[i]);
        }

        url = Address.getInstance().getURL(type);
        client.get(MyApplication.getContet(), url, params, handler);

    }

//    public void mypageTest(){
//
//        url = "http://172.20.10.11:8080/drive/user/mypage.pd";
//
//
//        client.get(MyApplication.getContet(), url, new TextHttpResponseHandler() {
//            @Override
//            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                Log.e(TAG,"false"+throwable.toString());
//            }
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, String responseString) {
//                Log.e(TAG,"success "+responseString);
//            }
//        });
//    }


}
