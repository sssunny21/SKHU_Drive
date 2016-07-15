package com.jhjg.skhu_drive.Controller;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.jhjg.skhu_drive.View.Folder_FileFragment;
import com.jhjg.skhu_drive.View.MainActivity;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by kangjungu1 on 2016. 5. 17..
 */
public class FileManager {

    public static final String TAG = "FileManager";
    private static final String dirPath = Environment.getExternalStorageDirectory().getPath() + "/skhu_drive";
    private final String GOOGLE_CHECKED_KEY = "googleCheck";
    private InputStream inputStream;
    private FileOutputStream fileStream;
    private File jFolder;
    private File jFile;
    private String checkFile,type,fileExtend;

    private static FileManager ourInstance = new FileManager();

    public static FileManager getInstance() {
        return ourInstance;
    }

    private FileManager() {
    }

    //downloads파일
    public void downloadGFileToJFolder(@Nullable Fragment f, String name, byte[] b) throws IOException {
        //가지고 있는 byte로 byte 스트림 생성
        inputStream = new ByteArrayInputStream(b);

        //기본 폴더 path
        jFolder = new File(dirPath);
        //기본 폴더가 없으면 생성
        if (!jFolder.exists()) {
            Log.e(TAG, "기본 폴더 생성");
            jFolder.mkdirs();
        }

        //만들파일명 넣어준다.
        jFile = new java.io.File(jFolder.getAbsolutePath() + "/" + name);

        Log.e(TAG, "jfile " + jFile.getPath());
        //이미파일이 존재할때
        if (jFile.exists()) {
            Log.e(TAG, "파일존재");
        } else {
            //파일이 존재하지 않을때
            fileStream = new FileOutputStream(jFile);
            byte buffer[] = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                fileStream.write(buffer, 0, length);
            }
            Log.e(TAG, "5");

            fileStream.close();
            inputStream.close();

            //google drive와 연동이 되어 있을때
            if (SettingManager.getInstance().getBoolean(GOOGLE_CHECKED_KEY)){
               GoogleFileHelper.getInstance().saveFileToDrive(jFile);
            }

            //adapter에 noti를 준다.
            if (f != null) {
                ((Folder_FileFragment) f).notiAdapter();
            }
        }
    }

    public boolean checkExistFile(String name) {
        checkFile = dirPath + "/" + name;
        jFolder = new File(checkFile);

        //파일 존재하지 않을때
        if (!jFolder.exists()) {
            Log.e(TAG, "파일존재x " + checkFile);
            return false;
        } else {
            //파일 존재 할때
            Log.e(TAG, "파일존재o " + checkFile);
            return true;
        }

    }

    public String getFilePath(String fileName) {
        return dirPath + "/" + fileName;
    }


    /**
     * 파일의 확장자 조회
     *
     * @param fileStr
     * @return
     */
    public String getExtension(String fileStr) {
        return fileStr.substring(fileStr.lastIndexOf(".") + 1, fileStr.length());
    }

    //TODO: 잘되는지 확인해보기
    public void viewFile(Context ctx, String fileName) {

        Intent fileLinkIntent = new Intent(Intent.ACTION_VIEW);
        //filePath를 얻어옴
        String filePath = getFilePath(fileName);
        //파일 만듬
        File file = new File(filePath);

        //확장자 구하기
        fileExtend = getExtension(file.getAbsolutePath());
        //소문자로 변경
        fileExtend = fileExtend.toLowerCase();

        //타입 구하기
        String type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtend);
        Log.e(TAG,"type22 "+type);

        fileLinkIntent.setDataAndType(Uri.fromFile(file), type);
        ((MainActivity)ctx).startActivityForResult(fileLinkIntent, 10);

    }

    public String getMIMEType(File file) {
        fileExtend = getExtension(file.getAbsolutePath());

        type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtend);
        return type;
    }

}
