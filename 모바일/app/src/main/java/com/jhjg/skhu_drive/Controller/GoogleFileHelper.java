


package com.jhjg.skhu_drive.Controller;

        import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.jhjg.skhu_drive.Support.MyApplication;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by kangjungu1 on 2016. 3. 24..
 */
public class GoogleFileHelper{

    public static final String TAG = "GoogleFileHelper";

    private static GoogleFileHelper instance = new GoogleFileHelper();

    public static GoogleFileHelper getInstance() {
        return instance;
    }
    private static final String[] SCOPES = {DriveScopes.DRIVE};//DRIVE_METADATA_READONLY};
    private HttpTransport transport;
    private JsonFactory jsonFactory;
    private static final String PREF_ACCOUNT_NAME = "accountName";

    //Service를 가져온다
    private Drive getService(){
        GoogleAccountCredential mCredential = GoogleAccountCredential.usingOAuth2(
                //metadata.readOnly로선언
                MyApplication.getContet(), Arrays.asList(SCOPES))
                //뭔지모르겠다.
                .setBackOff(new ExponentialBackOff())
                //계좌이름?과 기본값을 null로 setting에 넣어서 setting값을 가져온다.
                .setSelectedAccountName(SettingManager.getInstance().getString(PREF_ACCOUNT_NAME));

        transport = AndroidHttp.newCompatibleTransport();

        //JsonFactory를 생성.
        jsonFactory = JacksonFactory.getDefaultInstance();

        //구글 드라이브 서비스를 가져온다.
        return new com.google.api.services.drive.Drive.Builder(
                transport, jsonFactory, mCredential)
                .setApplicationName("Drive API Android skhu_drive")
                .build();
    }

    public void saveFileToDrive(final java.io.File file) {
        final Drive service = getService();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    // File's binary content
                    java.io.File fileContent = new java.io.File(file
                            .getPath());
                    FileContent mediaContent = new FileContent(FileManager.getInstance().getMIMEType(file), fileContent);

                    // File's metadata.
                    File body = new File();
                    body.setName(fileContent.getName());
                    body.setMimeType(FileManager.getInstance().getMIMEType(file));

                    File file = service.files().create(body, mediaContent)
                            .execute();
                    if (file != null) {
                        Log.e(TAG, "구글 업로드 성공");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }


}

