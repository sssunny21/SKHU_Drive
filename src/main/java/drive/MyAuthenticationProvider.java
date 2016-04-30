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
public class MyAuthenticationProvider implements  AuthenticationProvider{
   @Autowired UserMapper userMapper;

   @Override
   public Authentication authenticate(Authentication authentication) throws AuthenticationException {
      String u_id = authentication.getName();
      String u_pw = authentication.getCredentials().toString();
      return authenticate(u_id, u_pw);
   }

   public Authentication authenticate(String u_id, String u_pw) throws AuthenticationException {
      User user = userMapper.selectByU_id(u_id);

      if (user == null) return null;
      if (user.getU_pw().equals(encryptPasswd(encryptPasswd(u_pw))) == false) return null;

      List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
      grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_1"));
      grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + user.getU_auth()));
      return new MyAuthenticaion(u_id, u_pw, grantedAuthorities, user);
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

   @Override
   public boolean supports(Class<?> authentication) {
      return authentication.equals(UsernamePasswordAuthenticationToken.class);
   }


   public class MyAuthenticaion extends UsernamePasswordAuthenticationToken {
      private static final long serialVersionUID = 1L;
      User user;

      public MyAuthenticaion (String u_id, String u_pw, List<GrantedAuthority> grantedAuthorities, User user) {
         super(u_id, u_pw, grantedAuthorities);
         this.user = user;
      }

      public User getUser() {
         return user;
      }

      public void setUser(User user) {
         this.user = user;
      }
   }
}
