package drive;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ProfessorService {

	@Autowired
	ProfessorMapper professorMapper;

	public String validateBeforeInsert(Professor professor) throws Exception {

		String s = professor.getP_name();
		if (StringUtils.isBlank(s))
			return "이름을 입력하세요.";

		s = professor.getP_email();
		if (StringUtils.isBlank(s))
			return "이메일을 입력하세요.";

		s = professor.getP_tel();
		if (StringUtils.isBlank(s))
			return "전화번호를 입력하세요.";
		
		// s가 int 타입.
		//s = professor.getP_id();
		//if (StringUtils.isBlank(s))
		//	return "로그인ID를 입력하세요.";

		s = professor.getP_qpw();
		if (StringUtils.isBlank(s))
			return "질문을 입력하세요.";

		s = professor.getP_aqw();
		if (StringUtils.isBlank(s))
			return "질문에 대한 답을 입력하세요.";


		Professor professor2=professorMapper.selectByP_id(s);
		if(professor2!=null)
			return "로그인ID가 중복됩니다.";
		return null;
	}

	public String validateBeforeUpdate(Professor professor) throws Exception {

		
		String s = professor.getP_name();
		if (StringUtils.isBlank(s))
			return "이름을 입력하세요.";

		s = professor.getP_email();
		if (StringUtils.isBlank(s))
			return "이메일을 입력하세요.";

		s = professor.getP_tel();
		if (StringUtils.isBlank(s))
			return "전화번호를 입력하세요.";

		s = professor.getP_qpw();
		if (StringUtils.isBlank(s))
			return "질문을 입력하세요.";

		s = professor.getP_aqw();
		if (StringUtils.isBlank(s))
			return "질문에 대한 답을 입력하세요.";

		return null;
	}


	public static Professor getCurrentProfessor() { 
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); 
		if (authentication instanceof MyAuthenticationProvider.MyAuthenticaion) 
			return ((MyAuthenticationProvider2.MyAuthenticaion) authentication).getProfessor(); 
		return null; 
	} 

	public static void setCurrentProfessor(Professor professor) {
		((MyAuthenticationProvider2.MyAuthenticaion) 
				SecurityContextHolder.getContext().getAuthentication()).setProfessor(professor); 

	}
}
