package drive;

import java.util.List;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
	@Autowired UserMapper userMapper;
	@Autowired UserService userService;
	@Autowired DriveMapper driveMapper;

	@RequestMapping("/home/index.pd")
    public String index(Model model) {
		//session.setAttribute("user", user);
		//User u = (User)session.getAttribute("user");
		if(userService.getCurrentUser()!=null){
		User u = (User)userService.getCurrentUser();
		System.out.println(u.getD_id());
		List<Drive> professor = userMapper.selectProfessor(u.getD_id());
		model.addAttribute("professor",professor);
		}
        return "home/index";
    }
	
    @RequestMapping(value="/home/login.pd", method=RequestMethod.GET)
    public String login(Model model) {
    	return "home/login";
    }
}
