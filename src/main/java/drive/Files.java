package drive;

public class Files {
	int files_id;
	String files_name;
	int files_size;
	String files_date;
	byte[] files_body;
	byte[] data;

	int folder_id;


	int sfiles_id;

	String sfiles_name;
	int sfiles_size;
	String sfiles_date;
	byte[] sfiles_body;
	byte[] sdata;

	int sfolder_id;
	int id;
	int share_id;

	public int getFiles_id() {
		return files_id;
	}

	public void setFiles_id(int files_id) {
		this.files_id = files_id;
	}

	public String getFiles_name() {
		return files_name;
	}

	public void setFiles_name(String files_name) {
		this.files_name = files_name;
	}

	public int getFiles_size() {
		return files_size;
	}

	public void setFiles_size(int files_size) {
		this.files_size = files_size;
	}

	public String getFiles_date() {
		return files_date;
	}

	public void setFiles_date(String files_date) {
		this.files_date = files_date;
	}

	public int getFolder_id() {
		return folder_id;
	}

	public void setFolder_id(int folder_id) {
		this.folder_id = folder_id;
	}
	public byte[] getFiles_body() {
		return files_body;
	}

	public void setFiles_body(byte[] files_body) {
		this.files_body = files_body;
	}
	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
	public int getSfiles_id() {
		return sfiles_id;
	}

	public void setSfiles_id(int sfiles_id) {
		this.sfiles_id = sfiles_id;
	}

	public String getSfiles_name() {
		return sfiles_name;
	}

	public void setSfiles_name(String sfiles_name) {
		this.sfiles_name = sfiles_name;
	}

	public int getSfiles_size() {
		return sfiles_size;
	}

	public void setSfiles_size(int sfiles_size) {
		this.sfiles_size = sfiles_size;
	}

	public String getSfiles_date() {
		return sfiles_date;
	}

	public void setSfiles_date(String sfiles_date) {
		this.sfiles_date = sfiles_date;
	}

	public byte[] getSfiles_body() {
		return sfiles_body;
	}

	public void setSfiles_body(byte[] sfiles_body) {
		this.sfiles_body = sfiles_body;
	}

	public byte[] getSdata() {
		return sdata;
	}

	public void setSdata(byte[] sdata) {
		this.sdata = sdata;
	}

	public int getSfolder_id() {
		return sfolder_id;
	}

	public void setSfolder_id(int sfolder_id) {
		this.sfolder_id = sfolder_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getShare_id() {
		return share_id;
	}

	public void setShare_id(int share_id) {
		this.share_id = share_id;
	}


}
