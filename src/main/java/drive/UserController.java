package drive;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.SystemPropertyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@SuppressWarnings("unused")
@Controller

public class UserController {
	@Autowired UserMapper userMapper;
	@Autowired DepartmentMapper departmentMapper;
	@Autowired UserService userService;

	@RequestMapping("/user/mypage.pd")
	public String mypage(Model model) {
		return "user/mypage";
	}


	@RequestMapping(value="/home/join.pd")
	public String join(User user, Model model) throws Exception{
		String message = userService.validateBeforeInsert(user);
		if (message == null){
			userMapper.insert(user);
			model.addAttribute("successMsg", "저장했습니다.");
		}else{
			model.addAttribute("errorMsg", message);
		}
		return "home/join";
	}


	@RequestMapping(value="/user/myinfo_edit.pd", method=RequestMethod.GET)
	public String myinfo_edit(Model model) {
		model.addAttribute("user", UserService.getCurrentUser());
		model.addAttribute("department", departmentMapper.selectAll());
		return "user/myinfo_edit";
	}

	@RequestMapping(value="/user/myinfo_edit.pd", method=RequestMethod.POST)
	public String myinfo_edit(User user, Model model) throws Exception {
		user.setId(UserService.getCurrentUser().getId());
		String message = userService.validateBeforeUpdate(user);
		if (message == null) {
			userMapper.user_update(user);
			UserService.setCurrentUser(user);
			model.addAttribute("successMsg", "저장했습니다.");
		} else{
			model.addAttribute("errorMsg", message);
		}
		model.addAttribute("user", UserService.getCurrentUser());
		model.addAttribute("department", departmentMapper.selectAll());

		return "user/myinfo_edit";
	}


	@RequestMapping(value="/user/myinfo_pw.pd", method=RequestMethod.GET)
	public String myinfo_pw(User user, Model model) {
		return "user/myinfo_pw";
	}

	@RequestMapping(value="/user/myinfo_pw.pd", method=RequestMethod.POST)
	public String myinfo_pw(Model model, User user) throws Exception {
		user.setId(UserService.getCurrentUser().getId());
		String message = userService.UpdatePw(user);	
		if (message == null) {
			userMapper.updatePW(user);
			model.addAttribute("successMsg", "저장했습니다.");
		} 
		else{
			model.addAttribute("errorMsg", message);
		}
		return "user/myinfo_pw";

	}
}