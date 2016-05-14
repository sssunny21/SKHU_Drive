package drive;

import java.security.MessageDigest;

public class Professor{
	int id2;
	String p_id;
	String p_pw;
	String p_name;
	String p_tel;
	String p_email;
	String p_code;
	String p_qpw;
	String p_aqw;
	int d_id;


	public int getId2() {
		return id2;
	}
	public void setId2(int id2) {
		this.id2 = id2;
	}
	public String getP_id() {
		return p_id;
	}
	public void setP_id(String p_id) {
		this.p_id = p_id;
	}
	public String getP_pw() {
		return p_pw;
	}
	public void setP_pw(String p_pw) {
		this.p_pw = p_pw;
	}
	public String getP_name() {
		return p_name;
	}
	public void setP_name(String p_name) {
		this.p_name = p_name;
	}
	public String getP_tel() {
		return p_tel;
	}
	public void setP_tel(String p_tel) {
		this.p_tel = p_tel;
	}
	public String getP_email() {
		return p_email;
	}
	public void setP_email(String p_email) {
		this.p_email = p_email;
	}
	public String getP_code() {
		return p_code;
	}
	public void setP_code(String p_code) {
		this.p_code = p_code;
	}
	public String getP_qpw() {
		return p_qpw;
	}
	public void setP_qpw(String p_qpw) {
		this.p_qpw = p_qpw;
	}
	public String getP_aqw() {
		return p_aqw;
	}
	public void setP_aqw(String p_aqw) {
		this.p_aqw = p_aqw;
	}
	public int getD_id() {
		return d_id;
	}
	public void setD_id(int d_id) {
		this.d_id = d_id;
	}


	public static String encryptPasswd2(String p_pw) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA1");
			byte[] hash = md.digest(p_pw.getBytes("UTF-8")); 

			StringBuilder sb = new StringBuilder(2*hash.length); 
			for(byte b : hash){ sb.append(String.format("%02x", b&0xff)); } 
			return sb.toString();
		}
		catch (Exception e) {
			return p_pw;
		}
	}

}