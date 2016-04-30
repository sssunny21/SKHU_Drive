package drive;

public class Files {
	int files_id;
	String files_name;
	String files_size;
	String files_date;
	
	int folder_id;
	
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

	public String getFiles_size() {
		return files_size;
	}

	public void setFiles_size(String files_size) {
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

}
