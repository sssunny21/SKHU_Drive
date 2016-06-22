package bbs1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

		@Autowired
		UserMapper userMapper;
		@Autowired
		DepartmentMapper departmentMapper;
		@Autowired
		UserService userService;
		
		@RequestMapping("/user/list.esun")
		public String list(Model model){
			model.addAttribute("list", userMapper.selectAll());
			return "user/list";
		}
		
		@RequestMapping(value="/user/edit.esun", method=RequestMethod.GET)
		public String edit(@RequestParam("id") int id, Model model){
			User user = userMapper.selectById(id);
			model.addAttribute("user", user);
			model.addAttribute("department", departmentMapper.selectAll());
			return "user/edit";
		}
		
		@RequestMapping(value="/user/edit.esun", method=RequestMethod.POST)
		public String edit(User user, Model model) throws Exception {
			String message = userService.validateBeforeUpdate(user);
			if(message == null){
				userMapper.update(user);
				model.addAttribute("success", "저장했습니다.");
			}else
				model.addAttribute("error", message);
			model.addAttribute("department", departmentMapper.selectAll());
			return "user/edit";
		}
}
