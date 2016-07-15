package com.jhjg.skhu_drive.View;//package com.jhjg.skhu_drive.View;
//
//import android.app.Activity;
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.content.IntentSender;
//import android.os.Bundle;
//import android.util.Log;
//
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.drive.Drive;
//
//// TODO: 2016. 5. 22. pcloud보고 바꾸기 , 저걸로 유지 할수 있을듯 credentail 부분 잘봐보기  
//public class GoogleDriveActivity extends Activity implements GoogleApiClient.ConnectionCallbacks,
//        GoogleApiClient.OnConnectionFailedListener {
//    private static final String TAG = "drive-quickstart";
//    private static final int REQUEST_CODE_CREATOR = 2;
//    private static final int REQUEST_CODE_RESOLUTION = 3;
//    private static final int RESULT_OK = 9999;
//    private static final int RESULT_CANCEL = -1;
//    private GoogleApiClient mGoogleApiClient;
//    private ProgressDialog progressDialog;
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        Log.e(TAG, "onResume");
//        if (mGoogleApiClient == null) {
//            progressDialog = progressDialog.show(this, "연결중","");
//            //구글 api client를 가져옴
//            mGoogleApiClient = new GoogleApiClient.Builder(this)
//                    .addApi(Drive.API)
//                    .addScope(Drive.SCOPE_FILE)
//                    .addConnectionCallbacks(this)
//                    .addOnConnectionFailedListener(this)
//                    .build();
//        }
//        //googleApiClient 연결
//        mGoogleApiClient.connect();
//    }
//
//    @Override
//    protected void onPause() {
//        Log.e(TAG, "onPause");
//        progressDialog.dismiss();
//
//        if (mGoogleApiClient != null) {
//            Log.e(TAG, "mGoogleApiClient != null");
//            mGoogleApiClient.disconnect();
//        }
//        super.onPause();
//    }
//
//    @Override
//    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
//        switch (requestCode) {
//            case REQUEST_CODE_CREATOR:
//                //파일 업로드 완료시
//                if (resultCode == RESULT_OK) {
//                    Log.e(TAG,"완료");
//                }
//                break;
//        }
//    }
//
//    @Override
//    public void onConnectionFailed(ConnectionResult result) {
//        //연결 실패시
//        Log.e(TAG, "GoogleApiClient connection failed: " + result.toString());
//        progressDialog.dismiss();
//        if (!result.hasResolution()) {
//            //에러 다이얼로그
//            Log.e(TAG,"code "+result.getErrorCode()+"\nmessage :"+result.getErrorMessage());
//            setResult(RESULT_CANCEL);
//            finish();
//            return;
//        }
//        // The failure has a resolution. Resolve it.
//        // Called typically when the app is not yet authorized, and an
//        // authorization
//        // dialog is displayed to the user.
//        try {
//            result.startResolutionForResult(this, REQUEST_CODE_RESOLUTION);
//        } catch (IntentSender.SendIntentException e) {
//            Log.e(TAG, "Exception while starting resolution activity", e);
//        }
//    }
//
//    @Override
//    public void onConnected(Bundle connectionHint) {
//        Log.e(TAG, "API client connected.");
//        setResult(RESULT_OK);
//        finish();
//    }
//
//    @Override
//    public void onConnectionSuspended(int cause) {
//        Log.e(TAG, "GoogleApiClient connection suspended");
//    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        setResult(RESULT_CANCEL);
//        finish();
//    }
//}


import android.accounts.AccountManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.FileList;
import com.jhjg.skhu_drive.Controller.SettingManager;
import com.jhjg.skhu_drive.R;
import com.jhjg.skhu_drive.Support.MyApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class GoogleDriveActivity extends AppCompatActivity {
    //자격증 변수
    GoogleAccountCredential mCredential;

    //프로그레스다이얼로그
    ProgressDialog mProgress;
    public static final String TAG = "GoogleDriveActivity";
    static final int REQUEST_ACCOUNT_PICKER = 1000;
    static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    private static final String PREF_ACCOUNT_NAME = "accountName";
    //DRIVE_METADATA_READONLY = https://www.googleapis.com/auth/drive.metadata.readonly이다
    private static final String[] SCOPES = {DriveScopes.DRIVE};//DRIVE_METADATA_READONLY};

    private com.google.api.services.drive.Drive mainService;
    private HttpTransport transport;
    private JsonFactory jsonFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google);
        Log.e(TAG, "oncreate");

        //프로그래스바만듬
        mProgress = new ProgressDialog(this);
        mProgress.setMessage("Calling Drive API ...");

        Log.e(TAG,"SettingManager.getInstance().getString(PREF_ACCOUNT_NAME): "+SettingManager.getInstance().getString(PREF_ACCOUNT_NAME));

        //인증서와 서비스 오브젝트 선언
        //SharedPreferences는 해당 프로세스(어플리케이션)내에 File 형태로 Data를 저장해 줍니다.
        // 그리고 해당 어플리케이션이 삭제되기 전까지 Data를 보관해 주는 기능을 합니다.
        mCredential = GoogleAccountCredential.usingOAuth2(
                //metadata.readOnly로선언
                MyApplication.getContet(), Arrays.asList(SCOPES))
                //뭔지모르겠다.
                .setBackOff(new ExponentialBackOff())
                //계정이름 넣기
                .setSelectedAccountName(SettingManager.getInstance().getString(PREF_ACCOUNT_NAME));


    }

    //onCreate() 이후에 불린다.
    @Override
    protected void onResume() {
        Log.e(TAG, "onResume");
        super.onResume();
        //구글플레이서비스가 설치되어있으면 refreshResults가 불린다.
        if (isGooglePlayServicesAvailable()) {
            Log.e(TAG, "isGooglePlayServicesAvailable");
            refreshResults();
        } else {
            //구글플레이서비스가 설치되어있지않으면 메세지출력
            Toast.makeText(GoogleDriveActivity.this, "Google Play Services required: after installing, close and relaunch this app.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
        mProgress.dismiss();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG, "onRestart");
    }

    //requestCode = 무슨 액티비티가 끝났는지 확인, resultCode = 어떤상황으로 끝났는지, data = 보내온 데이터
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            //구글플레이서비스가 설치되어있지 않은 경우 다이얼로그가 불리는데,그 다이얼로그에서 빠져나온 이후
            case REQUEST_GOOGLE_PLAY_SERVICES:
                Log.e(TAG, "REQUEST_GOOGLE_PLAY_SERVICES");
                //구글플레이서비스가 제대로 설치됬으면 다시 확인해본다.
                if (resultCode != RESULT_OK) {
                    isGooglePlayServicesAvailable();
                }
                break;
            //맨 처음 이메일이 등록되어있지 않은경우, 새로운 액티비티가 불리는데, 이메일을 등록하고 나온경우.
            case REQUEST_ACCOUNT_PICKER:
                //이메일을 제대로 등록했고, 데이터가 null이 아닌경우
                if (resultCode == RESULT_OK && data != null &&
                        data.getExtras() != null) {
                    //인증서 이름을 가져오는듯
                    String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    //인증서가 있는경우?
                    if (accountName != null) {
                        mCredential.setSelectedAccountName(accountName);
                        //메일을 연결한 것을 저장해 놓는다.
                        SettingManager.getInstance().setString(PREF_ACCOUNT_NAME,accountName);
                        Log.e(TAG, mCredential.getGoogleAccountManager().getAccountByName(accountName).toString());
                        Log.e(TAG, "accoutName " + accountName);

                    }
                } else if (resultCode == RESULT_CANCELED) {
                    //이메일을 등록하지 않고 나온경우 메세지 출력
                    Toast.makeText(GoogleDriveActivity.this, "Account unspecified.", Toast.LENGTH_LONG).show();
                }
                break;
            case REQUEST_AUTHORIZATION:
                Log.e(TAG, "REQUEST_AUTHORIZATION");
                //UserRecoverableAuthIOException에서 불린다.
                if (resultCode != RESULT_OK) {
                    // 이메일을 선택할수 있는 메소드 호출
                    chooseAccount();
                }
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    //이메일을 아직알지못하면 chooseAccount()메소드를 불러서 이메일을 pick 하게한다.
    private void refreshResults() {
        Log.e(TAG, "refreshResults "+mCredential.getSelectedAccountName());
        //자격증명에서 이름을 불러왔는데 없으면 새로운 이메일을 pick
        if (mCredential.getSelectedAccountName() == null) {
            chooseAccount();
        } else {//자격증명이 있는경우
            if (isDeviceOnline()) {//인터넷에 연결되어있는경우
                //MakeRequestTask에 현재인증서를 넘기고 Thread를 실행한다.
                new MakeRequestTask(mCredential).execute();
            } else {//인터넷에연결이안되있는경우
                Toast.makeText(GoogleDriveActivity.this, "No network connection available.", Toast.LENGTH_LONG).show();

            }
        }
    }

    //위의 refreshResults()에서 등록된 아이디가 없어 못불러온경우
    public void chooseAccount() {
        Log.e(TAG, "chooseAccount");
        //새로운 액티비티를 연다.
        startActivityForResult(
                mCredential.newChooseAccountIntent(), REQUEST_ACCOUNT_PICKER);
    }

    //장비가 네트워크에 연결되어있는지 아닌지 확인하는 메소드
    private boolean isDeviceOnline() {
        Log.e(TAG, "isDeviceOnline");
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    //구글플레이서비스가 설치되어있고 업데이트가 잘되어있는지 확인해주는 메소드
    private boolean isGooglePlayServicesAvailable() {
        Log.e(TAG, "isGooglePlayServicesAvailable");
        final int connectionStatusCode =
//                GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
                GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        //구글플레이서비스 연결에 에러가 나는경우
//        if (GooglePlayServicesUtil.isUserRecoverableError(connectionStatusCode)) {
        if (GoogleApiAvailability.getInstance().isUserResolvableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
            return false;
        } else if (connectionStatusCode != ConnectionResult.SUCCESS) {
            return false;
        }
        return true;
    }

    //구글플레이서비스 연결에 에러가 나는경우 이 다이얼로그가 불린다.
    void showGooglePlayServicesAvailabilityErrorDialog(
            final int connectionStatusCode) {
        Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(GoogleDriveActivity.this, connectionStatusCode, REQUEST_GOOGLE_PLAY_SERVICES);
        dialog.show();
    }


    //API를부르는 작업은 새로운 Thread에서 작업을 해줘야한다. MainThread= UiThread이므로 네트워크 작업등은 따로 관리해줘야함.
    // 계속 요청을 보내는 통로와 응답을 받는 통로를 따로 만들어두면 async방식이라고 부른다. 하나의 클래스에서 thread를 사용할때 사용
    private class MakeRequestTask extends AsyncTask<Void, Void, List<String>> {

        private com.google.api.services.drive.Drive mService;
        private Exception mLastError;
        private FileList result;

        //AsyncTask 생성자 생성
        public MakeRequestTask(GoogleAccountCredential credential) {
            Log.e(TAG, "MakeRequestTask 생성자");
            // API 인증서를 매개변수로 받아온다.
            //httpTransport를 선언한다.  HTTP 연결 클래스 결정
            transport = AndroidHttp.newCompatibleTransport();

            //JsonFactory를 생성.
            jsonFactory = JacksonFactory.getDefaultInstance();

            //구글 드라이브 서비스를 가져온다.
            mService = new com.google.api.services.drive.Drive.Builder(
                    transport, jsonFactory, credential)
                    .setApplicationName("Drive API Android skhu_drive")
                    .build();

            mainService = mService;
        }

        //쓰레드 백그라운드에서 작업할 것을 선언해주는 메소드
        @Override
        protected List<String> doInBackground(Void... params) {
            try {
                // 파일의아이디와 이름이 들어있는 List<String> fileInfo;를 리턴받아 다시 리턴함
                Log.e(TAG, "doInBackground 실행");

                return getDataFromApi();
            } catch (Exception e) {
                //에러나면 캐치
                Log.e(TAG, "에러");
                //에러메세지
                mLastError = e;
                cancel(true);
                return null;
            }
        }

        //구글 드라이브에서 아이템을 가져온다. 파일을 찾을수 없으면 빈 리스트 보여줌
        private List<String> getDataFromApi() throws IOException {
            Log.e(TAG, "getDataFromApi");

            List<String> fileInfo = new ArrayList<String>();
            //함수가 불릴때 file list를 null로 초기화해줌
            Log.e(TAG, "1");

            String pageToken = null;

            //file을 가져와서 fileList에 저장
            result = mainService.files().list()
                    .setFields("nextPageToken, files(id, name, kind, webContentLink, mimeType, modifiedTime, parents )")
                    .setPageToken(pageToken)
                    //root에 있는것만 가져온다.
                    .setQ("'root'" + " in parents and trashed = false")
                    .execute();

            Log.e(TAG,"result "+result.getFiles().get(0).getName());

            return fileInfo;
        }


        //작업이 시작되기 전에 호출되며 UI스레드에서 실행되는 메소드,계산을 위한 초기화나 프로그래스 대화상자를 준비하는 등의 작업을 수행한다.
        //doInBackground가 실행되기전에 먼저 실행되야할 것이있으면 여기서 실행함.
        @Override
        protected void onPreExecute() {
            Log.e(TAG, "onPreExecute");
            mProgress.show();
        }

        // doInBackground에서드의 작업 결과를 UI반영하는 역할을 담당하는 메소드
        @Override
        protected void onPostExecute(List<String> output) {
            Log.e(TAG, "onPostExecute");
            //프로그레스를 숨김
            mProgress.hide();
            setResult(RESULT_OK);
            finish();
        }

        //AsyncTask가 종료될때
        @Override
        protected void onCancelled() {
            Log.e(TAG, "onCancelled");
            //프로그레스를 숨긴다.
            mProgress.hide();
            if (mLastError != null) {//오류가 발생했을때
                //instanceof를 이용한 연산결과로 true를 얻었다는 것은 참조변수가 검사한 타입으로 형변환이 가능하다는 것을 뜻함.
                if (mLastError instanceof GooglePlayServicesAvailabilityIOException) {
                    Log.e(TAG, "GooglePlayServicesAvailabilityIOException");
                    showGooglePlayServicesAvailabilityErrorDialog(
                            ((GooglePlayServicesAvailabilityIOException) mLastError)
                                    .getConnectionStatusCode());
                } else if (mLastError instanceof UserRecoverableAuthIOException) {
                    Log.e(TAG, "UserRecoverableAuthIOException");
                    GoogleDriveActivity.this.startActivityForResult(
                            ((UserRecoverableAuthIOException) mLastError).getIntent(),
                            GoogleDriveActivity.REQUEST_AUTHORIZATION);

                } else {
                    Log.e(TAG, "The following error occurred : " + mLastError.getMessage());
                }
            } else {//제대로 실행됬을 경우
            }
        }


    }


}
