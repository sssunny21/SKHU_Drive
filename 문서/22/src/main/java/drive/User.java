package drive;

import java.security.MessageDigest;

public class User {
	int id;
	String u_id;
	String u_pw;
	String u_name;
	String u_birth;
	String u_tel;
	int u_group;

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
	public String getU_birth() {
		return u_birth;
	}
	public void setU_birth(String u_birth) {
		this.u_birth = u_birth;
	}
	public String getU_tel() {
		return u_tel;
	}
	public void setU_tel(String u_tel) {
		this.u_tel = u_tel;
	}
	public int getU_group() {
		return u_group;
	}
	public void setU_group(int u_group) {
		this.u_group = u_group;
	}
}
