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
   @Autowired DepartmentMapper departmentMapper;

   @RequestMapping("/home/index.pd")
   public String index(Model model) throws Exception {
      if(UserService.getCurrentUser()!=null){
      User u = (User)UserService.getCurrentUser();
      List<Drive> professor = userMapper.selectProfessor(u.getD_id());
      model.addAttribute("professor",professor);
      List<Folder> myfolder = userMapper.selectMyFolder(u.getId()); 
      model.addAttribute("myfolder",myfolder);
      List<Drive> mydrive = userMapper.selectMyDrive(u.getId());
      model.addAttribute("mydrive",mydrive);
      Drive user_drive = userMapper.selectDrive(u.getId());//나의 드라이브
      model.addAttribute("drive",user_drive);
      String auth = userService.printAuth(u);//권한표시
      model.addAttribute("auth",auth);
      
      }
        return "home/index";
    }
   
    @RequestMapping(value="/home/login.pd", method=RequestMethod.GET)
    public String login(Model model) {
       return "home/login";
    }
    
    @RequestMapping("/include/sidebar.pd") 
    public String sidebar(Model model) throws Exception{
    	if(UserService.getCurrentUser()!=null){
    	      User u = (User)UserService.getCurrentUser();
    	      List<Folder> myfolder = userMapper.selectMyFolder(u.getId()); 
    	      model.addAttribute("myfolder",myfolder);
    	      }
    	return "include/sidebar";
    }
    
    @RequestMapping("/include/menu.pd") 
    public String menu(Model model) throws Exception{
    	if(UserService.getCurrentUser()!=null){
    	      User u = (User)UserService.getCurrentUser();
    	      String auth = userService.printAuth(u);//권한표시
    	     model.addAttribute("auth",auth);
		      Drive user_drive = userMapper.selectDrive(u.getId());//나의 드라이브
		      model.addAttribute("drive",user_drive);
    	      }
    	return "include/menu";
    }
    
    
    @RequestMapping(value="/home/sechPW.pd", method=RequestMethod.GET)
    public String sechPW(User user, Model model) {
       model.addAttribute("user", user);
       return "home/sechPW";
    }


    @RequestMapping(value="/home/sechPW.pd", method=RequestMethod.POST)
    public String sechPW(Model model, User user) throws Exception {
       User user2=userMapper.selectByU_id(user.u_id);
       System.out.println(user);
       if(user2!=null){
          String message = userService.SechePw(user);   // 아이디, 비밀번호찾기 질문, 비밀번호 찾기 답 작성여부,해당 아이디를 가진 유저가 있는지 확인
          if (message == null) {
             //User user2=userMapper.selectByU_id(user.u_id);
             boolean qpw = user.getU_qpw().equals(user2.getU_qpw());//비밀번호찾기 질문, 비밀번호 찾기 일치여부
             boolean apw= user.getU_apw().equals(user2.getU_apw());
             if (qpw&&apw==true) {
                user = userMapper.checkQA(user);
                model.addAttribute("id", user.id);
                return "redirect:/home/changePW.pd";
             }else{
                model.addAttribute("errorMsg", "일치하는 회원이 없습니다.");
             }
          } else{
             model.addAttribute("errorMsg", message);
          }
       }else{
          model.addAttribute("errorMsg", "아이디가 존재하지 않습니다.");
       }
       return "home/sechPW";
    }

    @RequestMapping(value="/home/changePW.pd", method=RequestMethod.GET)
    public String changePW(@RequestParam("id") int id , Model model) {
       User user=userMapper.selectById(id);
       model.addAttribute("user", user);
       return "home/changePW";
    }

    @RequestMapping(value="/home/changePW.pd", method=RequestMethod.POST)
    public String changePW(Model model, User user) throws Exception {
       String message = userService.changePW(user);   
       if (message == null) {
          userMapper.changePW(user);
          model.addAttribute("successMsg", "저장했습니다.");
       } 
       else{
          model.addAttribute("errorMsg", message);
       }
       return "home/changePW";
    }
    
    @RequestMapping("/home/access_denied.pd")
    public String access_denied(Model model) throws Exception {
    	return "home/access_denied";
    }
}
