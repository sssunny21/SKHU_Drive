package drive;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.sun.xml.internal.bind.v2.runtime.output.StAXExStreamWriterOutput;

@Service
public class UserService {

	@Autowired
	UserMapper userMapper;

	public String validateBeforeInsert(User user) throws Exception {
		String s = user.getU_name();
		if (StringUtils.isBlank(s))
			return "이름을 입력하세요.";

		s = user.getU_email();
		if (StringUtils.isBlank(s))
			return "이메일을 입력하세요.";

		s = user.getU_tel();
		if (StringUtils.isBlank(s))
			return "전화번호를 입력하세요.";


		s = user.getU_id();
		if (StringUtils.isBlank(s))
			return "로그인ID를 입력하세요.";

		s = user.getU_qpw();
		if (StringUtils.isBlank(s))
			return "질문을 입력하세요.";

		s = user.getU_apw();
		if (StringUtils.isBlank(s))
			return "질문에 대한 답을 입력하세요.";


		User user2=userMapper.selectByU_id(s);
		if(user2!=null)
			return "로그인ID가 중복됩니다.";
		return null;
	}

	public String validateBeforeUpdate(User user) throws Exception {


		String s = user.getU_name();
		if (StringUtils.isBlank(s))
			return "이름을 입력하세요.";

		s = user.getU_email();
		if (StringUtils.isBlank(s))
			return "이메일을 입력하세요.";

		s = user.getU_tel();
		if (StringUtils.isBlank(s))
			return "전화번호를 입력하세요.";


		s = user.getU_qpw();
		if (StringUtils.isBlank(s))
			return "질문을 입력하세요.";

		s = user.getU_apw();
		if (StringUtils.isBlank(s))
			return "질문에 대한 답을 입력하세요.";


		return null;


	}

	public String UpdatePw(User user) throws Exception {

		
		String s = user.getU_email();
		if (StringUtils.isBlank(s))
			return "이메일을 입력하세요.";
		//이메일이 틀릴때 

		s = user.getU_pw();
		if (StringUtils.isBlank(s))
			return "변경할패스워드를 입력하세요.";
		
		User user2=userMapper.selectById(user.getId());
		if(user2.getU_email().equals(user.getU_email())==false)
			return "이메일이 일치하지 않습니다.";

			
		return null;

	}
	public String SechePw(User user) throws Exception {

	      String s = user.getU_id();
	      if (StringUtils.isBlank(s))
	         return "아이디 입력하세요.";

	      s = user.getU_qpw();
	      if (StringUtils.isBlank(s))
	         return "비밀번호 찾기 질문을 입력하세요.";

	      s = user.getU_apw();
	      if (StringUtils.isBlank(s))
	         return "비밀번호 찾기 답을  입력하세요.";
	 
	      return null;
	   }

	   public String changePW(User user) throws Exception {

	      String s = user.getU_pw();
	      if (StringUtils.isBlank(s))
	         return "패스워드를 입력하세요.";

	      return null;

	   }
	   
	   public String printAuth(User user) throws Exception {
		   String name = user.getU_name();
		   int auth = user.getU_auth();
		   String print=null;
		   if(auth == 1){print="학생 "+name;}
		   else if(auth == 2){print=name+" 교수님";}
		   
		   return print;
	   }


	public static User getCurrentUser() { 
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); 
		if (authentication instanceof MyAuthenticationProvider.MyAuthenticaion) 
			return ((MyAuthenticationProvider.MyAuthenticaion) authentication).getUser(); 
		return null; 
	} 

	public static void setCurrentUser(User user) {
		((MyAuthenticationProvider.MyAuthenticaion) 
				SecurityContextHolder.getContext().getAuthentication()).setUser(user); 

	}
}
