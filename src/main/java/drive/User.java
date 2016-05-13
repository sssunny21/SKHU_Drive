package drive;

import java.security.MessageDigest;

public class User {
	int id;
	String u_id;
	String u_pw;
	String u_name;
	String u_tel;
	String u_email;
	int u_grade;
	String u_qpw;
	String u_apw;
	int u_auth;
	int d_id;
	int join_id;
	int folder_id;

	public static String encryptPasswd(String u_pw) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA1");
			byte[] hash = md.digest(u_pw.getBytes("UTF-8")); 

			StringBuilder sb = new StringBuilder(2*hash.length); 
			for(byte b : hash){ sb.append(String.format("%02x", b&0xff)); } 
			return sb.toString();
		}
		catch (Exception e) {
			return u_pw;
		}
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getU_id() {
		return u_id;
	}
	public void setU_id(String u_id) {
		this.u_id = u_id;
	}
	public String getU_pw() {
		return u_pw;
	}

	public void setU_pw(String u_pw) {
		this.u_pw = encryptPasswd(u_pw);
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

	public int getU_grade() {
		return u_grade;
	}

	public void setU_grade(int u_grade) {
		this.u_grade = u_grade;
	}

	public String getU_qpw() {
		return u_qpw;
	}

	public void setU_qpw(String u_qpw) {
		this.u_qpw = u_qpw;
	}

	public String getU_apw() {
		return u_apw;
	}

	public void setU_apw(String u_apw) {
		this.u_apw = u_apw;
	}

	public int getU_auth() {
		return u_auth;
	}

	public void setU_auth(int u_auth) {
		this.u_auth = u_auth;
	}

	public int getD_id() {
		return d_id;
	}

	public void setD_id(int d_id) {
		this.d_id = d_id;
	}
	public int getJoin_id() {
		return join_id;
	}

	public void setJoin_id(int join_id) {
		this.join_id = join_id;
	}

	public int getFolder_id() {
		return folder_id;
	}

	public void setFolder_id(int folder_id) {
		this.folder_id = folder_id;
	}
}
