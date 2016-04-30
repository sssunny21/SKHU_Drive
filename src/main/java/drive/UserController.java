package drive;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
	@Autowired UserMapper userMapper;
	@Autowired UserService userService;

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

	@RequestMapping(value="/user/myinfo.pd", method = RequestMethod.GET)
	public String myinfo(@RequestParam("id") Integer id, Model model) {
		User user = userMapper.selectById(id);
		model.addAttribute("user", user);
		return "user/myinfo";
	}

	@RequestMapping(value="/user/myinfo.pd", method = RequestMethod.POST)
	public String myinfo(User user, Model model) throws Exception{
		String message = userService.validateBeforeUpdate(user);
		if (message == null){
			userMapper.user_update(user);
			model.addAttribute("successMsg", "저장했습니다.");
		}else{
			model.addAttribute("errorMsg", message);
		}
		return "user/myinfo";
	}

}
