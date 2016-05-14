package drive;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminControllerJSON {
	@Autowired UserMapper userMapper;
	@Autowired UserService userService;
	
	@RequestMapping(value="/admin/editJSON.pd", method = RequestMethod.GET)
	public @ResponseBody User edit(@RequestParam("id") Integer id) {
		User user = userMapper.selectById(id);
		return user;
	}

	
}