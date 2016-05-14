
package drive;

import java.security.MessageDigest;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;



@Component
public class MyAuthenticationProvider2 implements  AuthenticationProvider{
	
	@Autowired ProfessorMapper professorMapper;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String p_id = authentication.getName();
		String p_pw = authentication.getCredentials().toString();
		return authenticate(p_id, p_pw);
	}


	public Authentication authenticate(String p_id, String p_pw) throws AuthenticationException {
		Professor professor = professorMapper.selectByP_id(p_id);

		if (professor == null) return null;
		if (professor.getP_pw().equals(encryptPasswd(encryptPasswd(p_pw))) == false) return null;

		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_1"));
		 grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + 1));
		return new MyAuthenticaion(p_id, p_pw, grantedAuthorities, professor);
	}
	public static String encryptPasswd(String p_pw) {
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

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}


	public class MyAuthenticaion extends UsernamePasswordAuthenticationToken {
		private static final long serialVersionUID = 1L;
		Professor professor;

		public MyAuthenticaion (String p_id, String p_pw, List<GrantedAuthority> grantedAuthorities, Professor professor) {
			super(p_id, p_pw, grantedAuthorities);
			this.professor = professor;
		}

		public Professor getProfessor() {
			return professor;
		}

		public void setProfessor(Professor professor) {
			this.professor = professor;
		}
	}
}
