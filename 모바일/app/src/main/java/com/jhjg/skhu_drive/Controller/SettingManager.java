package com.jhjg.skhu_drive.Controller;

import android.app.Activity;
import android.content.SharedPreferences;

import com.jhjg.skhu_drive.Support.MyApplication;

/**
 * Created by kangjungu1 on 2016. 5. 22..
 */
public class SettingManager {

    private final static String SETTING_PREFS = "setting";
    private final static String ACTION_VIEW = "ACTION";

    //true이면 grid, false이면 list
    public static String getActionView() {
        return ACTION_VIEW;
    }

    private SharedPreferences setting;
    private SharedPreferences.Editor editor;
    private static SettingManager ourInstance = new SettingManager();

    public static SettingManager getInstance() {
        return ourInstance;
    }

    private SettingManager(){
        this.setting = MyApplication.getContet().getSharedPreferences(SETTING_PREFS, Activity.MODE_PRIVATE);
        this.editor = setting.edit();
    }

    public void setBoolean(String key,boolean value){
        editor.putBoolean(key,value);
        editor.apply();
    }

    public boolean getBoolean(String key){
        return setting.getBoolean(key,false);
    }

    public void setString(String key,String value){
        editor.putString(key,value);
        editor.apply();
    }

    public String getString(String key){
        return setting.getString(key,null);
    }

    public void remove(String key){
        editor.remove(key);
        editor.apply();
    }
}
