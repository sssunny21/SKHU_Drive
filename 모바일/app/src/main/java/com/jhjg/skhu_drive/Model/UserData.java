package com.jhjg.skhu_drive.Model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by kangjungu1 on 2016. 5. 18..
 */
public class UserData extends RealmObject {

    public static final String FIELD_ID = "a_id";
    public static final String FIELD_U_ID = "u_id";
    public static final String FIELD_U_NAME = "u_name";
    public static final String FIELD_U_TEL = "u_tel";
    public static final String FIELD_U_EMAIL = "u_email";
    public static final String FIELD_U_GRADE = "u_grade";
    public static final String FIELD_U_QPW = "u_qpw";
    public static final String FIELD_D_ID = "d_id";


    @PrimaryKey
    private long a_id;

    private String u_id;
    private String u_name;
    private String u_tel;
    private String u_email;
    private String u_grade;
    private String u_qpw;
    private String d_id;

    public UserData(){}


    public UserData(String[] args){
        u_id = args[0];
        u_name = args[2];
        u_tel = args[3];
        u_email = args[4];
        u_grade = args[5];
        u_qpw = args[6];
        d_id = args[8];

    }


    public long getA_id() {
        return a_id;
    }

    public void setA_Id(long a_id) {
        this.a_id = a_id;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public String getU_tel() {
        return u_tel;
    }

    public void setU_tel(String u_tel) {
        this.u_tel = u_tel;
    }

    public String getU_email() {
        return u_email;
    }

    public void setU_email(String u_email) {
        this.u_email = u_email;
    }

    public String getU_grade() {
        return u_grade;
    }

    public void setU_grade(String u_grade) {
        this.u_grade = u_grade;
    }

    public String getU_qpw() {
        return u_qpw;
    }

    public void setU_qpw(String u_qpw) {
        this.u_qpw = u_qpw;
    }


    public String getD_id() {
        return d_id;
    }

    public void setD_id(String d_id) {
        this.d_id = d_id;
    }
}
