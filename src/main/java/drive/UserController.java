package drive;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.SystemPropertyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@SuppressWarnings("unused")
@Controller

public class UserController {
	@Autowired UserMapper userMapper;
	@Autowired DriveMapper driveMapper;
	@Autowired DepartmentMapper departmentMapper;
	@Autowired UserService userService;

	@RequestMapping("/user/mypage.pd")
	public String mypage(Model model) {
		if(UserService.getCurrentUser()!=null){
			User u = (User)UserService.getCurrentUser();
			List<Folder> myfolder = userMapper.selectMyFolder(u.getId()); 
			model.addAttribute("myfolder",myfolder);
			List<Drive> mydrive = userMapper.selectMyDrive(u.getId());
			model.addAttribute("mydrive",mydrive);
		      Drive user_drive = userMapper.selectDrive(u.getId());//나의 드라이브
		      model.addAttribute("drive",user_drive);
		}
		return "user/mypage";
	}

	@RequestMapping(value="/home/join_main.pd", method=RequestMethod.GET)
	public String join_main(User user, Model model) {
		return "home/join_main";
	}
	
	@RequestMapping(value="/home/join_main.pd", method=RequestMethod.POST, params="cmd=p_code")
	public String join_main(Model model,User user) throws Exception{
		String message = userService.validateBeforeInsertP(user);
		if (message == null){
			return "redirect:/home/professor_join.pd";
		}else{
			model.addAttribute("errorMsg", message);
		}
		return "home/join_main";
	}
	
	@RequestMapping(value="/home/professor_join.pd")
	public String professor_join(User user, Model model) throws Exception{
		User user2=userMapper.selectByU_id(user.u_id);
		if(user2==null){
		String message = userService.validateBeforeInsert(user);
		if (message == null){
			userMapper.insertP(user);
			userMapper.insert_drive(user);
			model.addAttribute("successMsg", "저장했습니다."); 
		}else{
			model.addAttribute("errorMsg", message);
		}
		}else{
			model.addAttribute("errorMsg", "해당 아이디가 존재합니다");
		}
		return "home/professor_join";
	}
	
	@RequestMapping(value="/home/user_join.pd")
	public String user_join(User user, Model model) throws Exception{
		User user2=userMapper.selectByU_id(user.u_id);
		if(user2==null){
		String message = userService.validateBeforeInsert(user);
		if (message == null){
			userMapper.insert(user);
			userMapper.insert_drive_user(user);
			model.addAttribute("successMsg", "저장했습니다.");
			
		}else{
			model.addAttribute("errorMsg", message);
		}
		}else{
			model.addAttribute("errorMsg", "해당 아이디가 존재합니다");
		}
		return "home/user_join";
	}
	
	@RequestMapping(value="/user/mypage.pd",method = RequestMethod.POST,params="cmd=user_out")
	public String user_out(Model model){
		User u = UserService.getCurrentUser();
		userMapper.delete(u.id);
		return "redirect:/home/logout.pd";
	}

	@RequestMapping(value="/user/mypage.pd",method = RequestMethod.POST,params="cmd=deleteFavorite")
	public String deletefavorite(@RequestParam("folder_id") int[] folder_id,Model model){
		User u = UserService.getCurrentUser();
		for(int i=0 ; i<folder_id.length; ++i){
			userMapper.deleteJoinFolder(folder_id[i]);
		}
		if(UserService.getCurrentUser()!=null){
			List<Folder> myfolder = userMapper.selectMyFolder(u.getId()); 
			model.addAttribute("myfolder",myfolder);
			List<Drive> mydrive = userMapper.selectMyDrive(u.getId());
			model.addAttribute("mydrive",mydrive);
			Drive user_drive = userMapper.selectDrive(u.getId());//나의 드라이브
			model.addAttribute("drive",user_drive);
		}
		return "user/mypage";
	}
	@RequestMapping(value="/user/mypage.pd",method = RequestMethod.POST,params="cmd=deleteFavorite2")
	public String deletefavoritedrive(@RequestParam("drive_id") int[] drive_id,Model model){
		User u = UserService.getCurrentUser();
		for(int i=0 ; i<drive_id.length; ++i){
			userMapper.deleteJoinDrive(drive_id[i]);
		}
			List<Folder> myfolder = userMapper.selectMyFolder(u.getId()); 
			model.addAttribute("myfolder",myfolder);
			List<Drive> mydrive = userMapper.selectMyDrive(u.getId());
			model.addAttribute("mydrive",mydrive);
			Drive user_drive = userMapper.selectDrive(u.getId());//나의 드라이브
			model.addAttribute("drive",user_drive);
		return "user/mypage";
	}


	@RequestMapping(value="/user/myinfo_edit.pd", method=RequestMethod.GET)
	public String myinfo_edit(Model model) {
		model.addAttribute("user", UserService.getCurrentUser());
		model.addAttribute("department", departmentMapper.selectAll());
    	if(UserService.getCurrentUser()!=null){
  	      User u = (User)UserService.getCurrentUser();
		      Drive user_drive = userMapper.selectDrive(u.getId());//나의 드라이브
		      model.addAttribute("drive",user_drive);
  	      }
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
    	if(UserService.getCurrentUser()!=null){
  	      User u = (User)UserService.getCurrentUser();
		      Drive user_drive = userMapper.selectDrive(u.getId());//나의 드라이브
		      model.addAttribute("drive",user_drive);
  	      }
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
	/**
	@RequestMapping(value="/user/myinfo_out.pd", method=RequestMethod.GET)
	public String myinfo_out(User user, Model model) {
    	if(UserService.getCurrentUser()!=null){
  	      User u = (User)UserService.getCurrentUser();
  	      }
		return "user/myinfo_out";
	}

	@RequestMapping(value="/user/myinfo_out.pd", method=RequestMethod.POST)
	public String myinfo_out(Model model, User user,Test test) throws Exception {
		user.setId(UserService.getCurrentUser().getId());
		test.setPassword(test.password);
		String message = userService.validatePw(user,test,test.getPassword());	
		if (message == null) {
			userMapper.delete(user.id);
			model.addAttribute("successMsg", "탈퇴되었습니다.");
		} 
		else{
			model.addAttribute("errorMsg", message);
		}
		return "user/myinfo_out";

	}
	**/
}