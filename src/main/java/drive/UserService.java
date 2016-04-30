package drive;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
        if(user2!=null && user.getId()!=user2.getId())
        	return "로그인ID가 중복됩니다.";
        return null;
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
