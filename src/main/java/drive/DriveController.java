package drive;

import java.util.List;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class DriveController {
	@Autowired DriveMapper driveMapper;
	@Autowired DriveService driveService;
	@Autowired UserService userService;
	@Autowired UserMapper userMapper;

	@RequestMapping(value="/pdrive/main.pd" ,method = RequestMethod.GET)
	public String main(@RequestParam("d_id") int d_id,Model model){
		List<Drive> drive = driveMapper.selectDriveInfo(d_id);
		model.addAttribute("main", drive);
		
		if(UserService.getCurrentUser()!=null){
			User u = (User)UserService.getCurrentUser();
			List<Folder> myfolder = userMapper.selectMyFolder(u.getId()); 
			model.addAttribute("myfolder",myfolder);
			List<Drive> mydrive = userMapper.selectMyDrive(u.getId());
			model.addAttribute("mydrive",mydrive);
		}
	
		return "pdrive/main";
	}
	
	@RequestMapping(value="/pdrive/main.pd",method = RequestMethod.POST,params="cmd=saveFavorites")
	public String saveFavorites3(@RequestParam("d_id") int d_id,@RequestParam("drive_id") int[] drive_id,Model model) {
		User u = (User)userService.getCurrentUser();
		for(int i=0 ; i<drive_id.length; ++i){
			driveMapper.insert_favorites_drive(u.id,drive_id[i]);
		}
		List<Drive> drive = driveMapper.selectDriveInfo(d_id);
		model.addAttribute("main", drive);
		List<Drive> mydrive = userMapper.selectMyDrive(u.getId());
		model.addAttribute("mydrive",mydrive);
		List<Folder> myfolder = userMapper.selectMyFolder(u.getId()); 
		model.addAttribute("myfolder",myfolder);
		
		return "pdrive/main";
	}

	@RequestMapping(value="/pdrive/folderList.pd" ,method = RequestMethod.GET)
	public String folderlist(@RequestParam("dr_id") int dr_id,Model model) {
		List<Folder> dr1 = driveMapper.selectBydr_id1(dr_id);
		List<Folder> dr2 = driveMapper.selectBydr_id2(dr_id);
		model.addAttribute("dr1", dr1);
		model.addAttribute("dr2", dr2);
		if(UserService.getCurrentUser()!=null){
			User u = (User)UserService.getCurrentUser();
			List<Folder> myfolder = userMapper.selectMyFolder(u.getId()); 
			model.addAttribute("myfolder",myfolder);
			List<Drive> mydrive = userMapper.selectMyDrive(u.getId());
			model.addAttribute("mydrive",mydrive);
		}
		return "pdrive/folderList";
	}

	@RequestMapping(value="/pdrive/folderList.pd",method = RequestMethod.POST,params="cmd=saveFavorites")
	public String saveFavorites(@RequestParam("dr_id") int dr_id,@RequestParam("folder_id") int[] folder_id,Model model) {
		User u = (User)userService.getCurrentUser();
		for(int i=0 ; i<folder_id.length; ++i){
			driveMapper.insert_favorites(u.id,folder_id[i]);
		}

		List<Folder> dr1 = driveMapper.selectBydr_id1(dr_id);
		List<Folder> dr2 = driveMapper.selectBydr_id2(dr_id);
		model.addAttribute("dr1", dr1);
		model.addAttribute("dr2", dr2);
		List<Folder> myfolder = userMapper.selectMyFolder(u.getId()); 
		model.addAttribute("myfolder",myfolder);
		return "pdrive/folderList";
	}

	@RequestMapping(value="/pdrive/folderList.pd",method = RequestMethod.POST,params="cmd=deleteFolder")
	public String deletefolder(@RequestParam("dr_id") int dr_id,@RequestParam("folder_id") int[] folder_id,Model model){
		Drive drive = new Drive();
		drive = driveMapper.selectDrive(dr_id);
		System.out.println("사용자id"+drive.getId());
		System.out.println("드라이브아이디"+drive.getDrive_id());
		if (driveService.isAuthor(drive)==true){
			for(int i=0 ; i<folder_id.length; ++i){
				driveMapper.deleteFolder(folder_id[i]);
			}
		}else{
			model.addAttribute("errorMsg", "폴더 삭제 권한이 없습니다.");
		}
		User u = (User)userService.getCurrentUser();
		List<Folder> dr1 = driveMapper.selectBydr_id1(dr_id);
		List<Folder> dr2 = driveMapper.selectBydr_id2(dr_id);
		model.addAttribute("dr1", dr1);
		model.addAttribute("dr2", dr2);
		List<Folder> myfolder = userMapper.selectMyFolder(u.getId()); 
		model.addAttribute("myfolder",myfolder);
		return "pdrive/folderList";
	}

	@RequestMapping(value="/pdrive/folderList2.pd",method = RequestMethod.POST,params="cmd=saveFavorites")
	public String saveFavorites2(@RequestParam("fd_id") int fd_id,@RequestParam("folder_id") int[] folder_id,Model model) {
		List<Folder> pr = driveMapper.selectBypr_id(fd_id);
		List<Folder> all = driveMapper.selectFolderAll();
		model.addAttribute("pr", pr);
		model.addAttribute("all",all);

		Folder folder = new Folder();
		folder.setFolder_id(fd_id);
		model.addAttribute("folder",folder);

		List<Files> fd = driveMapper.selectByf_id(fd_id);
		model.addAttribute("fd", fd);
		if(UserService.getCurrentUser()!=null){
			User u = (User)UserService.getCurrentUser();
			for(int i=0 ; i<folder_id.length; ++i){
				driveMapper.insert_favorites(u.id,folder_id[i]);
			}
			List<Folder> myfolder = userMapper.selectMyFolder(u.getId()); 
			model.addAttribute("myfolder",myfolder);
			
		}
		return "pdrive/folderList2";
	}
	@RequestMapping(value="/pdrive/folderList2.pd",method = RequestMethod.POST,params="cmd=deleteFolder")
	public String deletefolder2(@RequestParam("folder_id") int[] folder_id,@RequestParam("dr_id") int dr_id,Model model){
		User u = (User)userService.getCurrentUser();
		for(int i=0 ; i<folder_id.length; ++i){
			driveMapper.deleteFolder(folder_id[i]);
			folderlist2(folder_id[i],dr_id,model);
		}
		
		List<Folder> myfolder = userMapper.selectMyFolder(u.getId()); 
		model.addAttribute("myfolder",myfolder);
		return "pdrive/folderList2";
	}


	@RequestMapping(value="/pdrive/folderList2.pd" ,method = RequestMethod.GET)
	public String folderlist2(@RequestParam("fd_id") int fd_id,@RequestParam("dr_id") int dr_id,Model model){
		List<Folder> pr = driveMapper.selectBypr_id(fd_id);
		List<Folder> all = driveMapper.selectFolderAll();
		model.addAttribute("pr", pr);
		model.addAttribute("all",all);

		Folder folder = new Folder();
		folder.setDrive_id(dr_id);
		folder.setFolder_id(fd_id);
		model.addAttribute("folder", folder);

		List<Files> fd = driveMapper.selectByf_id(fd_id);
		model.addAttribute("fd", fd);
		if(UserService.getCurrentUser()!=null){
			User u = (User)UserService.getCurrentUser();
			List<Folder> myfolder = userMapper.selectMyFolder(u.getId()); 
			model.addAttribute("myfolder",myfolder);
			List<Drive> mydrive = userMapper.selectMyDrive(u.getId());
			model.addAttribute("mydrive",mydrive);
		}
		return "pdrive/folderList2";
	}//서브 폴더 리스트를 보여주는 액션메소드

	@RequestMapping(value="/pdrive/folderList2.pd" ,method = RequestMethod.POST,params="cmd=createfolder2")
	public String createfolder2(@RequestParam("fd_id") int fd_id,@RequestParam("dr_id") int dr_id,Folder folder,Model model) throws Exception {
		String message = driveService.validatepw(folder);
		driveMapper.insert_folder2(folder);
		return "redirect:/pdrive/folderList2.pd?fd_id="+fd_id+"&dr_id="+dr_id;
	}//폴더 생성 createfolder2
	
	@RequestMapping(value="/pdrive/folderList2.pd" ,method = RequestMethod.POST)
	public String folderlist2(@RequestParam("fd_id") int fd_id,HttpServletRequest request,@RequestParam("file") MultipartFile uploadedFile,Model model)throws IOException {
		Files files = new Files();
		files.setFolder_id(fd_id);
		files.setFiles_name(Paths.get(uploadedFile.getOriginalFilename()).getFileName().toString());
		files.setFiles_size((int)uploadedFile.getSize());
		files.setData(uploadedFile.getBytes());
		files.setFiles_body(uploadedFile.getBytes());
		driveMapper.insert_files(files);

		return "redirect:/pdrive/folderList2.pd?fd_id="+fd_id;
	}
	@RequestMapping(value="/pdrive/folderList2.pd",method = RequestMethod.POST,params="cmd=deleteFiles")
	public String deletefiles(@RequestParam("fd_id") int fd_id,@RequestParam("files_id") int[] files_id,Model model){
		User u = (User)userService.getCurrentUser();
		for(int i=0 ; i<files_id.length; ++i){
			driveMapper.deleteFiles(files_id[i]);
		}

		List<Folder> myfolder = userMapper.selectMyFolder(u.getId()); 
		model.addAttribute("myfolder",myfolder);
		return "redirect:/pdrive/folderList2.pd?fd_id="+fd_id;
	}

	@RequestMapping(value="/popup/createFolder.pd",method = RequestMethod.GET)
	public String createFolder(@RequestParam("dr_id") Integer dr_id,Model model){
		Folder folder = driveMapper.selectBydr_id(dr_id);
		model.addAttribute("folder", folder);
		return "popup/createFolder";
	}

	@RequestMapping(value="/popup/createFolder.pd",method = RequestMethod.POST)
	public String createFolder(Folder folder,Model model) throws Exception {
		String message = driveService.validatepw(folder);
		if (message == null){
			driveMapper.insert_sfolder(folder);
			model.addAttribute("successMsg", "저장했습니다.");
		}else{
			driveMapper.insert_folder(folder);
			model.addAttribute("successMsg", "저장했습니다.");
		}
		return "popup/createFolder";
	}
	/**
	@RequestMapping(value="/popup/createFile.pd",method = RequestMethod.GET)
	public String createFile(@RequestParam("dr_id") Integer dr_id,Model model){
		model.addAttribute("folder", driveMapper.selectBydr_id1(dr_id));
		return "popup/createFile";
	}

	@RequestMapping(value="/popup/createFile.pd",method = RequestMethod.POST)
	public String createFile(Folder folder,@RequestParam("file") MultipartFile uploadedFile,Model model)throws Exception{
		Files files = new Files();
		files.setFolder_id(1);
		files.setFiles_name(Paths.get(uploadedFile.getOriginalFilename()).getFileName().toString());
		files.setFiles_size((int)uploadedFile.getSize());
		files.setData(uploadedFile.getBytes());
		files.setFiles_body(uploadedFile.getBytes());
		driveMapper.insert_files(files);
		return "redirect:/pdrive/folderList2.pd?fd_id="+;
	}
	 **/

	@RequestMapping("/pdrive/download.pd")
	public void download(@RequestParam("id") int id, HttpServletResponse response) throws IOException { 
		Files files = driveMapper.selectByff_id(id);
		if (files == null) return; 
		String fileName = URLEncoder.encode(files.getFiles_name(),"UTF-8"); 
		response.setContentType("application/octet-stream"); 
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ";"); 
		try (BufferedOutputStream output = new BufferedOutputStream(response.getOutputStream())) { 
			output.write(files.getFiles_body()); 
		} 
	}

}