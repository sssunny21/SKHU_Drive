package drive;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller

public class UserControllerJSON {
	@Autowired
	UserMapper userMapper;
	@Autowired
	DriveMapper driveMapper;
	@Autowired
	DepartmentMapper departmentMapper;
	@Autowired
	UserService userService;

	@RequestMapping("/user/mypageJSON.pd")
	public @ResponseBody List<Folder> mypage() {
		if (UserService.getCurrentUser() != null) {
			User u = (User) UserService.getCurrentUser();
			return userMapper.selectMyFolder(u.getId());
		}
		return null;
	}
}
