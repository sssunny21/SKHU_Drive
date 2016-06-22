package drive;

import java.security.MessageDigest;

public class Test {
	String password;
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = encryptPasswd(password);
	}

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
}
