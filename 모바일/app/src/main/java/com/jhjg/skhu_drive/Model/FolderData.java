package com.jhjg.skhu_drive.Model;

/**
 * Created by kangjungu1 on 2016. 4. 10..
 */
public class FolderData extends ParentFile{
    private long drive_id;
    private String drive_name;
    private long d_id;
    private long id2;
    private long folder_id;
    private String folder_name;
    private long parent_id;
    private long sfolder_id;
    private String sfolder_name;
    private String sfolder_pw;

    @Override
    public int getType() {
        return super.getType();
    }

    @Override
    public void setType(int type) {
        super.setType(type);
    }

    public long getDrive_id() {
        return drive_id;
    }

    public void setDrive_id(long drive_id) {
        this.drive_id = drive_id;
    }

    public String getDrive_name() {
        return drive_name;
    }

    public void setDrive_name(String drive_name) {
        this.drive_name = drive_name;
    }

    public long getD_id() {
        return d_id;
    }

    public void setD_id(long d_id) {
        this.d_id = d_id;
    }

    public long getId2() {
        return id2;
    }

    public void setId2(long id2) {
        this.id2 = id2;
    }

    public long getFolder_id() {
        return folder_id;
    }

    public void setFolder_id(long folder_id) {
        this.folder_id = folder_id;
    }

    public String getFolder_name() {
        return folder_name;
    }

    public void setFolder_name(String folder_name) {
        this.folder_name = folder_name;
    }

    public long getParent_id() {
        return parent_id;
    }

    public void setParent_id(long parent_id) {
        this.parent_id = parent_id;
    }

    public long getSfolder_id() {
        return sfolder_id;
    }

    public void setSfolder_id(long sfolder_id) {
        this.sfolder_id = sfolder_id;
    }

    public String getSfolder_name() {
        return sfolder_name;
    }

    public void setSfolder_name(String sfolder_name) {
        this.sfolder_name = sfolder_name;
    }

    public String getSfolder_pw() {
        return sfolder_pw;
    }

    public void setSfolder_pw(String sfolder_pw) {
        this.sfolder_pw = sfolder_pw;
    }
}
