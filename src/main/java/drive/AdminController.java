package drive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {
	@Autowired UserMapper userMapper;
	@Autowired UserService userService;
	@Autowired DepartmentMapper departmentMapper;
	
	@RequestMapping("/admin/user_list.pd") 
	public String user_list(Model model,Pagination pagination) { 
		pagination.setRecordCount(userMapper.selectCount(pagination));
		model.addAttribute("list", userMapper.selectPage(pagination)); 
		return "admin/user_list"; 
	} 
	
	
	
	@RequestMapping(value="/admin/user_edit.pd", method = RequestMethod.GET)
	public String edit(@RequestParam("id") Integer id,Pagination pagination, Model model) {
		User user = userMapper.selectById(id);
		model.addAttribute("user", user);
		model.addAttribute("department", departmentMapper.selectAll());
		return "admin/user_edit";
	}

	@RequestMapping(value="/admin/user_edit.pd", method = RequestMethod.POST)
	public String edit(User user,Pagination pagination, Model model) throws Exception{
		String message = userService.validateBeforeUpdate(user);
		if (message == null){
			userMapper.update(user);
			//model.addAttribute("successMsg", "저장했습니다.");
			return "redirect:/admin/user_list.pd";
		}else{
			model.addAttribute("errorMsg", message);
			return "admin/user_edit";
		}

	}
	 
	
}
