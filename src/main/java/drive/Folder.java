package drive;

public class Folder {
	int drive_id;
	String drive_name;
	int d_id;
	int folder_id;
	String folder_name;
	int parent_id;
	
	int sfolder_id;
	String sfolder_name;
	String sfolder_pw;
	
	int id;
	int join_id;
	
	public int getDrive_id() {
		return drive_id;
	}
	public void setDrive_id(int drive_id) {
		this.drive_id = drive_id;
	}
	public String getDrive_name() {
		return drive_name;
	}
	public void setDrive_name(String drive_name) {
		this.drive_name = drive_name;
	}
	public int getD_id() {
		return d_id;
	}
	public void setD_id(int d_id) {
		this.d_id = d_id;
	}
	
	public int getFolder_id() {
		return folder_id;
	}
	public void setFolder_id(int folder_id) {
		this.folder_id = folder_id;
	}
	public String getFolder_name() {
		return folder_name;
	}
	public void setFolder_name(String folder_name) {
		this.folder_name = folder_name;
	}
	public int getParent_id() {
		return parent_id;
	}
	public void setParent_id(int parent_id) {
		this.parent_id = parent_id;
	}
	public int getSfolder_id() {
		return sfolder_id;
	}
	public void setSfolder_id(int sfolder_id) {
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
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getJoin_id() {
		return join_id;
	}
	public void setJoin_id(int join_id) {
		this.join_id = join_id;
	}
	
}
