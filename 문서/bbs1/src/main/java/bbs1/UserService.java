package bbs1;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

		@Autowired
		UserMapper userMapper;
		
		public String validateBeforeInsert(User user)throws Exception{
			String s = user.getName();
			if(StringUtils.isBlank(s))
				return "�̸��� �Է����ּ���.";
			
			s = user.getEmail();
			if (StringUtils.isBlank(s))
	            return "�̸��� �ּҸ� �Է��ϼ���.";

	        s = user.getUserType();
	        if (StringUtils.isBlank(s))
	            return "����� ������ �����ϼ���.";

	        s = user.getLoginId();
	        if (StringUtils.isBlank(s))
	            return "�α���ID�� �Է��ϼ���.";

	        User user2 = userMapper.selectByLoginID(s);
	        if (user2 != null)
	            return "�α���ID�� �ߺ��˴ϴ�.";

	        return null;
		}
		
		public String validateBeforeUpdate(User user) throws Exception {
			String s = user.getName();
			if (StringUtils.isBlank(s))
	            return "�̸��� �Է��ϼ���.";

	        s = user.getEmail();
	        if (StringUtils.isBlank(s))
	            return "�̸��� �ּҸ� �Է��ϼ���.";

	        s = user.getUserType();
	        if (StringUtils.isBlank(s))
	            return "����� ������ �����ϼ���.";

	        s = user.getLoginId();
	        if (StringUtils.isBlank(s))
	            return "�α���ID�� �Է��ϼ���.";

	        User user2 = userMapper.selectByLoginID(s);
	        if (user2 != null && user.getId() != user2.getId())
	            return "�α���ID�� �ߺ��˴ϴ�.";
	        
	        return null;
		}
}
