package com.jhjg.skhu_drive.Model;

/**
 * Created by kangjungu1 on 2016. 4. 10..
 */
public class FileData extends ParentFile{
    private long files_id;
    private String files_name;
    private long files_size;
    private String files_date;
    private long folder_id;
    private String files_body;


    @Override
    public int getType() {
        return super.getType();
    }

    @Override
    public void setType(int type) {
        super.setType(type);
    }

    public long getFiles_id() {
        return files_id;
    }

    public void setFiles_id(long files_id) {
        this.files_id = files_id;
    }

    public String getFiles_name() {
        return files_name;
    }

    public void setFiles_name(String files_name) {
        this.files_name = files_name;
    }

    public long getFiles_size() {
        return files_size;
    }

    public void setFiles_size(long files_size) {
        this.files_size = files_size;
    }

    public String getFiles_date() {
        return files_date;
    }

    public String getFiles_body() {
        return files_body;
    }

    public void setFiles_body(String files_body) {
        this.files_body = files_body;
    }

    public void setFiles_date(String files_date) {
        this.files_date = files_date;
    }

    public long getFolder_id() {
        return folder_id;
    }

    public void setFolder_id(long folder_id) {
        this.folder_id = folder_id;
    }
}
