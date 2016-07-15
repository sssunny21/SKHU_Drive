package com.jhjg.skhu_drive.Support;

/**
 * Created by kangjungu1 on 2016. 5. 16..
 */
public class Address {
    private static final String DOMAIN = "172.20.10.11";
    private static final String BASE_URL = "http://"+DOMAIN+":8080";
    private static final String MAIN_PROFESSOR_DRIVE_URL = "/drive/pdrive/mainJSON.pd";
    private static final String FOLDER_LIST1_URL = "/drive/pdrive/folderListJSON.pd";
    private static final String FOLDER_LIST2_URL = "/drive/pdrive/folderList2JSON.pd";
    private static final String FILE_LIST_URL = "/drive/pdrive/folderList2JSON2.pd";
    private static final String JOIN_URL = "/drive/home/join.pd";
    private static final String LOGIN_URL = "/drive/home/login_processing.pd";
    private static final String DOWNLOAD_URL = "/drive/pdrive/downloadJSON.pd";
    private static final String MY_FAVORITE_FOLDER_URL ="/drive/user/mypageJSON.pd";

    //type
    private final int PROFESSOR_TYPE = 0;
    private final int MAIN_FOLDER_TYPE = 1;
    private final int FOLDER_FILE_TYPE = 2;
    private final int FILE_TYPE = 3;
    private final int DOWNLOAD_TYPE = 4;
    private final int MY_FAVORITE_FOLDER_TYPE = 5;

    private String url;

    private static Address ourInstance = new Address();

    public static Address getInstance() {
        return ourInstance;
    }

    private Address() {
    }

    //URL RETURN
    //맨처음 페이지
    public String getProfessorURL(){
        return BASE_URL+MAIN_PROFESSOR_DRIVE_URL;
    }

    //처음 폴더 눌렀을경우
    public String getMainFolderURL(){
        return BASE_URL+FOLDER_LIST1_URL;
    }

    //폴더 눌러서 안에 들어온 경우
    public String getFolderFileURL(){
        return BASE_URL+FOLDER_LIST2_URL;
    }

    public String getJoinURL(){
        return BASE_URL+JOIN_URL;
    }

    public String getLoginURL(){
        return BASE_URL+LOGIN_URL;
    }

    public String getFileURL(){
        return BASE_URL+FILE_LIST_URL;
    }

    public String getDownloadUrl(){
        return BASE_URL+DOWNLOAD_URL;
    }

    public String getMyFavoriteFolderUrl(){return BASE_URL+MY_FAVORITE_FOLDER_URL;}

    public String getDomain(){ return DOMAIN;}


    //TYPE RETURN
    public  int getFolderFileType() {
        return FOLDER_FILE_TYPE;
    }

    public  int getProfessorType() {
        return PROFESSOR_TYPE;
    }

    public  int getMainFolderType() {
        return MAIN_FOLDER_TYPE;
    }

    public int getFILE_TYPE() {
        return FILE_TYPE;
    }

    public int getDOWNLOAD_TYPE(){
        return DOWNLOAD_TYPE;
    }

    public int getMY_FAVORITE_FOLDER_TYPE(){return MY_FAVORITE_FOLDER_TYPE;}


    //타입에따라 URL 리턴
    public String getURL(int type){
        if(type==PROFESSOR_TYPE){
            url = getProfessorURL();
        }else if(type==MAIN_FOLDER_TYPE){
            url = getMainFolderURL();
        }else if (type==FOLDER_FILE_TYPE){
            url = getFolderFileURL();
        }else if(type == FILE_TYPE){
            url = getFileURL();
        }else if(type == DOWNLOAD_TYPE){
            url = getDownloadUrl();
        }else if(type == MY_FAVORITE_FOLDER_TYPE){
            url = getMyFavoriteFolderUrl();
        }

        return url;
    }


}
