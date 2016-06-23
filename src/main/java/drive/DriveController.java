package drive;

import java.util.Arrays;
import java.util.List;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller
public class DriveController {
	@Autowired DriveMapper driveMapper;
	@Autowired DriveService driveService;
	@Autowired UserService userService;
	@Autowired UserMapper userMapper;
	@Autowired DepartmentMapper departmentMapper;

	/*************************드라이브*************************/
	@Secured("ROLE_1")
	@RequestMapping(value="/pdrive/main.pd" ,method = RequestMethod.GET)
	public String main(@RequestParam("d_id") int d_id,Model model) {
		List<Drive> drive = driveMapper.selectDriveInfo(d_id);
		model.addAttribute("main", drive);

		if(UserService.getCurrentUser()!=null){
			User u = (User)UserService.getCurrentUser();
			List<Folder> myfolder = userMapper.selectMyFolder(u.getId()); 
			model.addAttribute("myfolder",myfolder);
			List<Drive> mydrive = userMapper.selectMyDrive(u.getId());
			model.addAttribute("mydrive",mydrive);
			Drive user_drive = userMapper.selectDrive(u.getId());//나의 드라이브
			model.addAttribute("drive",user_drive);
		}

		return "pdrive/main";
	}
	@Secured("ROLE_1")
	@RequestMapping(value="/pdrive/main.pd",method = RequestMethod.POST,params="cmd=saveFavorites")
	public String saveFavorites3(@RequestParam("d_id") int d_id,@RequestParam("drive_id") int[] drive_id,Model model) {
		User u = (User)userService.getCurrentUser();
		/**
		driveMapper.insert_favorites_drive(u.id,dr_id);
		System.out.println(dr_id);
		 **/
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
	}//드라이브 즐겨찾기

	/*************************폴더*************************/
	@Secured("ROLE_1")
	@RequestMapping(value="/pdrive/folderList.pd" ,method = RequestMethod.GET)
	public String folderlist(@RequestParam("dr_id") int dr_id,Model model) {
		if(UserService.getCurrentUser()!=null){
			User u = (User)UserService.getCurrentUser();

			List<Folder> dr1 = driveMapper.selectBydr_id1(dr_id);
			List<Folder> dr2 = driveMapper.selectBydr_id2(dr_id);
			model.addAttribute("dr1", dr1);
			model.addAttribute("dr2", dr2);

			Folder folder = new Folder();
			folder.setDrive_id(dr_id);
			model.addAttribute("folder",folder);
			model.addAttribute("dn",driveMapper.selectDrive(dr_id));

			List<Folder> myfolder = userMapper.selectMyFolder(u.getId()); 
			model.addAttribute("myfolder",myfolder);
			List<Drive> mydrive = userMapper.selectMyDrive(u.getId());
			model.addAttribute("mydrive",mydrive);
			Drive user_drive = userMapper.selectDrive(u.getId());//나의 드라이브
			model.addAttribute("drive",user_drive);
		}
		return "pdrive/folderList";
	}//드라이브 바로 아래 폴더리스트

	@Secured("ROLE_1")
	@RequestMapping(value="/pdrive/folderList.pd" ,method = RequestMethod.POST,params="cmd=createfolder")
	public String createfolder(@RequestParam("dr_id") int dr_id,Folder folder,RedirectAttributes redirectAttributes) throws Exception {
		Drive drive = new Drive();
		drive = driveMapper.selectDrive(dr_id);
		if (driveService.isAuthor(drive)){//드라이브 소유자 검사
			if(driveService.isCreateShare(drive)==true){//공유폴더는 교수님만 생성 가능
				if(folder.getShare()==1){
					driveMapper.insert_sfolder(folder);
				}else{
					driveMapper.insert_folder(folder);
				}
			}else{
				if(folder.getShare()==1){
					redirectAttributes.addFlashAttribute("errorMsg", "공유 폴더는 생성 불가능합니다.");
				}else{
					driveMapper.insert_folder(folder);
				}
			}
		}else{
			redirectAttributes.addFlashAttribute("errorMsg", "폴더 생성 권한이 없습니다.");
		}
		return "redirect:/pdrive/folderList.pd?dr_id="+dr_id;
	}//폴더 생성 createfolder

	@Secured("ROLE_1")
	@RequestMapping(value="/pdrive/folderList.pd",method = RequestMethod.POST,params="cmd=saveFavorites")
	public String saveFavorites(@RequestParam("dr_id") int dr_id,@RequestParam("folder_id") int[] folder_id,Model model) {
		User u = UserService.getCurrentUser();
		Folder folder = new Folder();
		for(int i=0 ; i<folder_id.length; ++i){
			driveMapper.insert_favorites(u.id,folder_id[i]);
			folder.setFolder_id(folder_id[i]);
			model.addAttribute("folder",folder);
		}

		List<Folder> dr1 = driveMapper.selectBydr_id1(dr_id);
		List<Folder> dr2 = driveMapper.selectBydr_id2(dr_id);
		model.addAttribute("dr1", dr1);
		model.addAttribute("dr2", dr2);

		List<Folder> myfolder = userMapper.selectMyFolder(u.getId()); 
		model.addAttribute("myfolder",myfolder);
		List<Drive> mydrive = userMapper.selectMyDrive(u.getId());
		model.addAttribute("mydrive",mydrive);
		Drive user_drive = userMapper.selectDrive(u.getId());//나의 드라이브
		model.addAttribute("drive",user_drive);

		return "pdrive/folderList";
	}//폴더 즐겨찾기

	@Secured("ROLE_1")
	@RequestMapping(value="/pdrive/folderList.pd",method = RequestMethod.POST,params="cmd=deleteFolder")
	public String deletefolder(@RequestParam("dr_id") int dr_id,@RequestParam("folder_id") int[] folder_id,RedirectAttributes redirectAttributes){
		Drive drive = new Drive();
		drive = driveMapper.selectDrive(dr_id);
		if (driveService.isAuthor(drive)){//폴더 삭제 권한 확인
			for(int i=0 ; i<folder_id.length; ++i){
				driveMapper.deleteFolder(folder_id[i]);
			}
			redirectAttributes.addFlashAttribute("successMsg", "삭제를 완료하였습니다.");
		}else{
			redirectAttributes.addFlashAttribute("errorMsg", "폴더 삭제 권한이 없습니다.");
		}
		return "redirect:/pdrive/folderList.pd?dr_id="+dr_id;
	}//폴더 삭제
	
	@Secured("ROLE_1")
	@RequestMapping(value="/pdrive/folderList.pd",method = RequestMethod.POST,params="cmd=editfolder")
	public String editfolder(@RequestParam("folder_id") int folder_id,@RequestParam("dr_id") int dr_id,Folder folder,RedirectAttributes redirectAttributes) throws Exception {
		folder.setFolder_id(folder_id);
		String message = driveService.editFolder(folder);
		Drive drive = new Drive();
		drive = driveMapper.selectDrive(dr_id);
		if (message == null){
			if (driveService.isAuthor(drive)){//폴더 수정 권한 확인
				driveMapper.update_folder(folder);
				redirectAttributes.addFlashAttribute("successMsg", "수정을 완료하였습니다.");
			}else{
				redirectAttributes.addFlashAttribute("errorMsg", "폴더 수정 권한이 없습니다.");
			}
		}else{
			redirectAttributes.addFlashAttribute("errorMsg", message);
		}
		return "redirect:/pdrive/folderList.pd?dr_id="+dr_id;
	}//폴더 수정
	
	@Secured("ROLE_1")
	@RequestMapping(value="/pdrive/folderList.pd",method = RequestMethod.POST,params="cmd=insfolder")
	public String insfolder(@RequestParam("sfolder_id") int sfolder_id,@RequestParam("dr_id") int dr_id,Folder folder,RedirectAttributes redirectAttributes) throws Exception {
		folder.setSfolder_id(sfolder_id);
		int count=driveMapper.inSfolder(folder);
		if (count==1){
			return "redirect:/pdrive/sfolderList2.pd?fd_id="+sfolder_id+"&dr_id="+dr_id;
		}else{
			redirectAttributes.addFlashAttribute("errorMsg", "비밀번호가 일치하지 않습니다");
		}
		return "redirect:/pdrive/folderList.pd?dr_id="+dr_id;
	}

	/*************************서브 폴더*************************/
	@Secured("ROLE_1")
	@RequestMapping(value="/pdrive/folderList2.pd" ,method = RequestMethod.GET)
	public String folderlist2(@RequestParam("fd_id") int fd_id,@RequestParam("dr_id") int dr_id,Model model) {
		List<Folder> pr = driveMapper.selectBypr_id(fd_id);
		List<Folder> all = driveMapper.selectFolderAll();
		model.addAttribute("pr", pr);
		model.addAttribute("all",all);

		Folder folder = new Folder();
		folder.setDrive_id(dr_id);
		folder.setFolder_id(fd_id);
		model.addAttribute("folder",folder);
		model.addAttribute("fn",driveMapper.selectFolder_name(fd_id));

		List<Files> fd = driveMapper.selectByf_id(fd_id);
		model.addAttribute("fd", fd);
		
		if(UserService.getCurrentUser()!=null){
			User u = (User)UserService.getCurrentUser();
			List<Folder> myfolder = userMapper.selectMyFolder(u.getId()); 
			model.addAttribute("myfolder",myfolder);
			List<Drive> mydrive = userMapper.selectMyDrive(u.getId());
			model.addAttribute("mydrive",mydrive);
			Drive user_drive = userMapper.selectDrive(u.getId());//나의 드라이브
			model.addAttribute("drive",user_drive);
		}
		return "pdrive/folderList2";
	}//서브 폴더 리스트를 보여주는 액션메소드

	@Secured("ROLE_1")
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
	}//서브 폴더 즐겨찾기

	@Secured("ROLE_1")
	@RequestMapping(value="/pdrive/folderList2.pd" ,method = RequestMethod.POST,params="cmd=createfolder2")
	public String createfolder2(@RequestParam("fd_id") int fd_id,@RequestParam("dr_id") int dr_id,Folder folder,RedirectAttributes redirectAttributes) throws Exception {
		String message = driveService.editsFolder(folder);
		Drive drive = new Drive();
		drive = driveMapper.selectDrive(dr_id);
		if (driveService.isAuthor(drive)){//폴더 생성 권한 확인
			driveMapper.insert_folder2(folder);
			redirectAttributes.addFlashAttribute("successMsg", "완료하였습니다.");
		}else{
			redirectAttributes.addFlashAttribute("errorMsg", "폴더 생성 권한이 없습니다.");
		}
		return "redirect:/pdrive/folderList2.pd?fd_id="+fd_id+"&dr_id="+dr_id;
	}//서브 폴더 생성

	@Secured("ROLE_1")
	@RequestMapping(value="/pdrive/folderList2.pd",method = RequestMethod.POST,params="cmd=deleteFolder2")
	public String deletefolder2(@RequestParam("fd_id") int fd_id,@RequestParam("folder_id") int[] folder_id,@RequestParam("dr_id") int dr_id,Model model,RedirectAttributes redirectAttributes){
		User u = UserService.getCurrentUser();
		Drive drive = new Drive();
		drive = driveMapper.selectDrive(dr_id);
		if (driveService.isAuthor(drive)){//폴더 삭제 권한 확인
			for(int i=0 ; i<folder_id.length; ++i){
				driveMapper.deleteFolder(folder_id[i]);
				folderlist2(folder_id[i],dr_id,model);
			}
			redirectAttributes.addFlashAttribute("successMsg", "삭제를 완료하였습니다.");
		}else{
			redirectAttributes.addFlashAttribute("errorMsg", "폴더 삭제 권한이 없습니다.");
		}

		return "redirect:/pdrive/folderList2.pd?fd_id="+fd_id+"&dr_id="+dr_id;
	}//서브 폴더 삭제

	@Secured("ROLE_1")
	@RequestMapping(value="/pdrive/folderList2.pd" ,method = RequestMethod.POST)
	public String folderlist2(@RequestParam("fd_id") int fd_id,@RequestParam("dr_id") int dr_id,HttpServletRequest request,@RequestParam("file") MultipartFile uploadedFile,RedirectAttributes redirectAttributes)throws IOException {
		Files files = new Files();
		files.setFolder_id(fd_id);
		files.setFiles_name(Paths.get(uploadedFile.getOriginalFilename()).getFileName().toString());
		files.setFiles_size((int)uploadedFile.getSize());
		files.setData(uploadedFile.getBytes());
		files.setFiles_body(uploadedFile.getBytes());
		Drive drive = new Drive();
		drive = driveMapper.selectDrive(dr_id);
		if (driveService.isAuthor(drive)){//폴더 삭제 권한 확인
			driveMapper.insert_files(files);
			redirectAttributes.addFlashAttribute("successMsg", "업로드를 완료하였습니다.");
		}else{
			redirectAttributes.addFlashAttribute("errorMsg", "파일 업로드 권한이 없습니다.");
		}
		return "redirect:/pdrive/folderList2.pd?fd_id="+fd_id+"&dr_id="+dr_id;
	}//서브 폴더 파일업로드

	@Secured("ROLE_1")
	@RequestMapping(value="/pdrive/folderList2.pd",method = RequestMethod.POST,params="cmd=deleteFiles")
	public String deletefiles(@RequestParam("fd_id") int fd_id,@RequestParam("dr_id") int dr_id,@RequestParam("files_id") int[] files_id,RedirectAttributes redirectAttributes){
		User u = (User)userService.getCurrentUser();
		Drive drive = new Drive();
		drive = driveMapper.selectDrive(dr_id);
		if (driveService.isAuthor(drive)){//폴더 삭제 권한 확인
			for(int i=0 ; i<files_id.length; ++i){
				driveMapper.deleteFiles(files_id[i]);
			}
			redirectAttributes.addFlashAttribute("successMsg", "삭제를 완료하였습니다.");
		}else{
			redirectAttributes.addFlashAttribute("errorMsg", "파일 삭제 권한이 없습니다.");
		}

		return "redirect:/pdrive/folderList2.pd?fd_id="+fd_id+"&dr_id="+dr_id;
	}//서브 폴더 파일 삭제

	@Secured("ROLE_1")
	@RequestMapping(value="/pdrive/folderList2.pd",method = RequestMethod.POST,params="cmd=editfolder2")
	public String editfolder2(@RequestParam("folder_id") int folder_id,@RequestParam("fd_id") int fd_id,@RequestParam("dr_id") int dr_id,Folder folder,RedirectAttributes redirectAttributes) throws Exception {
		folder.setFolder_id(folder_id);
		String message = driveService.editFolder(folder);
		Drive drive = new Drive();
		drive = driveMapper.selectDrive(dr_id);
		if (message == null){
			if (driveService.isAuthor(drive)){
				System.out.println(folder.getFolder_id());
				System.out.println(folder.getFolder_name());
				driveMapper.update_folder(folder);
				redirectAttributes.addFlashAttribute("successMsg", "수정을 완료했습니다.");
			}else{
				redirectAttributes.addFlashAttribute("errorMsg", "폴더 수정 권한이 없습니다.");
			}
		}else{
			redirectAttributes.addFlashAttribute("errorMsg", message);
		}
		return "redirect:/pdrive/folderList2.pd?fd_id="+fd_id+"&dr_id="+dr_id;
	}//서브 폴더 수정

	@Secured("ROLE_1")
	@RequestMapping("/pdrive/download.pd")
	public void download(@RequestParam("id") int id, HttpServletResponse response) throws IOException { 
		Files files = driveMapper.selectByff_id(id);
		String fileName = URLEncoder.encode(files.getFiles_name(),"UTF-8"); 
		response.setContentType("application/octet-stream"); 
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ";"); 
		try (BufferedOutputStream output = new BufferedOutputStream(response.getOutputStream())) { 
			output.write(files.getFiles_body()); 
		} 
	}//파일 다운로드

	@Secured("ROLE_2")
	@RequestMapping("/pdrive/s_download.pd")
	public void s_download(@RequestParam("id") int id, HttpServletResponse response) throws IOException { 
		Files sfiles = driveMapper.selectBysff_id(id);
		if (sfiles == null) return; 
		String sfileName = URLEncoder.encode(sfiles.getSfiles_name(),"UTF-8"); 
		response.setContentType("application/octet-stream"); 
		response.setHeader("Content-Disposition", "attachment;filename=" + sfileName + ";"); 
		try (BufferedOutputStream output = new BufferedOutputStream(response.getOutputStream())) { 
			output.write(sfiles.getSfiles_body()); 
		}	
	}//공유 파일 다운로드
	
	@RequestMapping("/pdrive/textPreview.pd") 
	public String showText(Model model, @RequestParam("id") int id,Folder folder,Drive drive) throws IOException {
		Files files = driveMapper.selectByff_id(id);
		if(driveService.badFileExtIsReturnBoolean(files)==true){
			model.addAttribute("fileData", convertToString(files.getFiles_body()));
			model.addAttribute("fileName", files.getFiles_name()); 
			User u = (User)UserService.getCurrentUser();
			List<Folder> myfolder = userMapper.selectMyFolder(u.getId()); 
			model.addAttribute("myfolder",myfolder);
			List<Drive> mydrive = userMapper.selectMyDrive(u.getId());
			model.addAttribute("mydrive",mydrive);
			Drive user_drive = userMapper.selectDrive(u.getId());
			model.addAttribute("drive",user_drive);
		}else{
			return "redirect:/home/index.pd";
		}
		return "pdrive/textPreview"; 
	}//텍스트파일 미리보기
	String convertToString(byte[] bytes) throws UnsupportedEncodingException { 
		if (bytes[0] == (byte)0xEF && bytes[1] == (byte)0xBB && bytes[2] == (byte)0xBF) 
			return new String(Arrays.copyOfRange(bytes, 3, bytes.length), "utf-8"); 
		return new String(bytes, "euc-kr"); 
	} 
	@RequestMapping("/pdrive/downloadImage.pd") 
	public void downloadImage(@RequestParam("id") int id, HttpServletResponse response) throws IOException {
		Files files = driveMapper.selectByff_id(id);
		if(driveService.badFileExtIsReturnBoolean(files)==true){
			Files files2 = driveMapper.selectByff_id(73);
			response.setContentType(getImageContentType(files2.getFiles_name())); 
			try (BufferedOutputStream output = new BufferedOutputStream(response.getOutputStream())) {
				output.write(files2.getFiles_body()); 
			}
		}else{
			response.setContentType(getImageContentType(files.getFiles_name())); 
			try (BufferedOutputStream output = new BufferedOutputStream(response.getOutputStream())) {
				output.write(files.getFiles_body()); 
			}
		}
	}
	@RequestMapping("/pdrive/downloadImage2.pd") 
	public void downloadImage2(@RequestParam("id") int id, HttpServletResponse response) throws IOException {
		Files files = driveMapper.selectBysff_id(id);
		if(driveService.badFileExtIsReturnBoolean2(files)==true){
			Files files2 = driveMapper.selectByff_id(73);
			response.setContentType(getImageContentType(files2.getFiles_name())); 
			try (BufferedOutputStream output = new BufferedOutputStream(response.getOutputStream())) {
				output.write(files2.getFiles_body()); 
			}
		}else{
			response.setContentType(getImageContentType(files.getSfiles_name())); 
			try (BufferedOutputStream output = new BufferedOutputStream(response.getOutputStream())) {
				output.write(files.getSfiles_body()); 
			}
		}
	} 
	private String getImageContentType(String fileName) {
		String contentType = "image/jpeg"; 
		int index = fileName.lastIndexOf('.'); 
		if (index > 0) { 
			String extension = fileName.substring(index + 1).toLowerCase();
			if (".png.gif.bmp.tiff".indexOf(extension) > 0)
				contentType = "image/" + extension; 
		} 
		return contentType; 
	} 

	/*************************공유 폴더*************************/


	@Secured("ROLE_1")
	@RequestMapping(value="/pdrive/sfolderList2.pd" ,method = RequestMethod.GET)
	public String sfolderList2(@RequestParam("fd_id") int fd_id,@RequestParam("dr_id") int dr_id,Model model) {

		List<Files> fd = driveMapper.selectBysf_id(fd_id);
		model.addAttribute("fd", fd);
		model.addAttribute("sn",driveMapper.selectShare_name(fd_id));

		if(UserService.getCurrentUser()!=null){
			User u = (User)UserService.getCurrentUser();
			List<Folder> myfolder = userMapper.selectMyFolder(u.getId()); 
			model.addAttribute("myfolder",myfolder);
			List<Drive> mydrive = userMapper.selectMyDrive(u.getId());
			model.addAttribute("mydrive",mydrive);
			Drive user_drive = userMapper.selectDrive(u.getId());//나의 드라이브
			model.addAttribute("drive",user_drive);
		}
		return "pdrive/sfolderList2";
	}//공유 폴더 리스트를 보여주는 액션메소드
	
	@Secured("ROLE_2")
	@RequestMapping(value="/pdrive/folderList.pd",method = RequestMethod.POST,params="cmd=deleteSFolder")
	public String deleteSfolder(@RequestParam("dr_id") int dr_id,@RequestParam("sfolder_id") int[] sfolder_id,RedirectAttributes redirectAttributes){
		Drive drive = new Drive();
		drive = driveMapper.selectDrive(dr_id);
		if (driveService.isAuthor(drive)){//폴더 삭제 권한 확인
			for(int i=0 ; i<sfolder_id.length; ++i){
				driveMapper.deleteSFolder(sfolder_id[i]);
			}
			redirectAttributes.addFlashAttribute("successMsg", "삭제를 완료하였습니다.");
		}else{
			redirectAttributes.addFlashAttribute("errorMsg", "폴더 삭제 권한이 없습니다.");
		}
		return "redirect:/pdrive/folderList.pd?dr_id="+dr_id;
	}//공유 폴더 삭제
	
	@Secured("ROLE_1")
	@RequestMapping(value="/pdrive/folderList.pd",method = RequestMethod.POST,params="cmd=editsfolder")
	public String editsfolder(@RequestParam("sfolder_id") int sfolder_id,@RequestParam("dr_id") int dr_id,Folder folder,RedirectAttributes redirectAttributes) throws Exception {
		folder.setFolder_id(sfolder_id);
		String message = driveService.editsFolder(folder);
		Drive drive = new Drive();
		drive = driveMapper.selectDrive(dr_id);
		if (message == null){
			if (driveService.isAuthor(drive)){//폴더 수정 권한 확인
				driveMapper.update_sfolder(folder);
				redirectAttributes.addFlashAttribute("successMsg", "수정을 완료했습니다.");
			}else{
				redirectAttributes.addFlashAttribute("errorMsg", "폴더 수정 권한이 없습니다.");
			}
		}else{
			redirectAttributes.addFlashAttribute("errorMsg", message);
		}
		return "redirect:/pdrive/folderList.pd?dr_id="+dr_id;
	}//공유 폴더 수정

	@Secured("ROLE_1")
	@RequestMapping(value="/pdrive/sfolderList2.pd" ,method = RequestMethod.POST)
	public String sfolderList2(@RequestParam("fd_id") int fd_id,@RequestParam("dr_id") int dr_id,HttpServletRequest request,@RequestParam("file") MultipartFile uploadedFile,RedirectAttributes redirectAttributes)throws IOException {
		Files sfiles = new Files();
		sfiles.setSfolder_id(fd_id);
		sfiles.setSfiles_name(Paths.get(uploadedFile.getOriginalFilename()).getFileName().toString());
		sfiles.setSfiles_size((int)uploadedFile.getSize());
		sfiles.setSdata(uploadedFile.getBytes());
		sfiles.setSfiles_body(uploadedFile.getBytes());

		Drive drive = new Drive();
		drive = driveMapper.selectDrive(dr_id);
		if (driveService.isAuthor(drive)){//폴더 삭제 권한 확인
			driveMapper.insert_sfiles(sfiles);
			redirectAttributes.addFlashAttribute("successMsg", "업로드를 완료하였습니다.");
		}else{
			redirectAttributes.addFlashAttribute("errorMsg", "파일 업로드 권한이 없습니다.");
		}
		return "redirect:/pdrive/sfolderList2.pd?fd_id="+fd_id+"&dr_id="+dr_id;
	}//공유 폴더 파일업로드

	@Secured("ROLE_2")
	@RequestMapping(value="/pdrive/sfolderList2.pd",method = RequestMethod.POST,params="cmd=deleteFiles")
	public String deleteSfiles(@RequestParam("fd_id") int fd_id,@RequestParam("dr_id") int dr_id,@RequestParam("sfiles_id") int[] sfiles_id,RedirectAttributes redirectAttributes){
		Drive drive = new Drive();
		drive = driveMapper.selectDrive(dr_id);
		if (driveService.isAuthor(drive)){//폴더 삭제 권한 확인
			for(int i=0 ; i<sfiles_id.length; ++i){
				driveMapper.deleteSFiles(sfiles_id[i]);
			}
			redirectAttributes.addFlashAttribute("successMsg", "삭제를 완료하였습니다.");
		}else{
			redirectAttributes.addFlashAttribute("errorMsg", "파일 삭제 권한이 없습니다.");
		}

		return "redirect:/pdrive/sfolderList2.pd?fd_id="+fd_id+"&dr_id="+dr_id;
	}//공유 폴더 파일 삭제


}