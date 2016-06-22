package drive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
// import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
	@Autowired UserMapper userMapper;
	@Autowired UserService userService;

	@RequestMapping("/home/index.pd")
    public String index(Model model) {
        return "home/index";
    }
    @RequestMapping(value="/home/login.pd", method=RequestMethod.GET)
    public String login(Model model) {
    	return "home/login";
    }

}
